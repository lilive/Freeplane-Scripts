import java.nio.file.Path
import java.nio.file.Paths
import org.freeplane.api.Node

/**
 * Take a string that contains code, and return a html formated version of it.
 * 
 * This function use the highlight software to format the code.
 * http://www.andre-simon.de/doku/highlight/en/highlight.php
 * The software must be installed, and the variable highlightPath must
 * contain the executable path.
 *
 * - Search for `--font` within the code to change the font
 * - Read the comments and modify the function `formatCodeInCoreText` to change
 *   the style applied to the node core.
 * - Read the comments and modify the function `formatCodeInDetails` to change
 *   the style applied to the node details.
 *
 * @param code The code text to format
 * @param language The code language (python, lisp, java, etc).
 *                 To get all the available languages type in a shell :
 *                 highlight.exe --list-scripts=langs
 * @param options Additionnal options to pass to highlight.
 *                eg: ["-F stroustrup"]
 */
def static getHTML( String code, String language, List<String> options = [] )
{
    String highlightPath = "C:\\Program Files\\highlight\\highlight.exe"
    
    Path tmpPath = Paths.get( System.getProperty( "java.io.tmpdir" ) ).toAbsolutePath()

    // Create a file that contains this node source code text
    Path inputPath = tmpPath.resolve( "lilive-format-code-input.txt" )

    // Take car to write the file in UTF-8
    java.io.BufferedWriter writer = new java.io.BufferedWriter(
        new java.io.OutputStreamWriter(
            new java.io.FileOutputStream( inputPath.toString() ),
            java.nio.charset.StandardCharsets.UTF_8
        )
    )

    // Save the code
    writer.write( code )
    writer.close()

    // File that will receive the html formated code
    Path outputPath = tmpPath.resolve( "lilive-format-code-output.html" )

    // Create the command line that convert the source code file to html
    String command =
        '"C:\\Program Files\\highlight\\highlight.exe"' +
        ' "' + inputPath.toString() + '"' +
        ' --syntax ' + language +
        ' --font "Source Code Pro" --font-size 12 --style edit-xcode' +
        ' -O html -I -u UTF-8 -f --enclose-pre --inline-css' +
        ' -o "' + outputPath.toString() + '"' +
        ' ' + options.join( " " )

    // Get the output of this conversion
    command.execute().waitForOrKill( 3000 )
    String html = new File( outputPath.toString() ).getText('UTF-8')

    // Delete the files
    new File( inputPath.toString() ).delete()
    new File( outputPath.toString() ).delete()
    // Tweak it a little for freeplane
    html = html.replaceAll( /background-color.*?;/, "" )
    html = html.replaceAll( "&apos;", "'" )
    html = html.replaceAll( />\s+<\/pre>/, "></pre>" )
    html = "<html><body>" + html.trim() + "</body></html>"

    return html
}

/**
 * Assuming the node core text is code, format it in colored HTML,
 * and apply the style named "Code" to this node.
 * This works because I have a style named "Code" in my maps.
 * 
 * @see getHTML() for parameters details.
 */
def static formatCodeInCoreText( Node node, String language, List<String> options = [] )
{
    String html = getHTML( node.plainText, language, options )
    node.text = html
    node.style.maxNodeWidth = 2000
    node.style.name = "Code"
}
/**
 * Apply formatCodeInCoreText( Node, String , String ) to each node of the list.
 */
def static formatCodeInCoreText( List<Node> nodes, String language, List<String> options = [] )
{
    nodes.each{ node -> formatCodeInCoreText( node, language, options ) }
}
/**
 * Assuming the node details text is code, format it in colored HTML,
 * and add the icon named "Code" to this node.
 * This works because I have an icon named "Code" in my user defined icons.
 * See https://docs.freeplane.org/attic/old-mediawiki-content/User_icons.html about user icons.
 *
 * @see getHTML() for parameters details.
 */
def static formatCodeInDetails( Node node, String language, List<String> options = [] )
{
    String html = getHTML( node.details.plain, language, options )
    node.details = html
    node.style.maxNodeWidth = 1500
    if( ! node.icons.contains( "Code" ) ) node.icons.add( "Code" )
}
/**
 * Apply formatCodeInDetails( Node, String , String ) to each node of the list.
 */
def static formatCodeInDetails( List<Node> nodes, String language, List<String> options = [] )
{
    nodes.each{ node -> formatCodeInDetails( node, language, options ) }
}
