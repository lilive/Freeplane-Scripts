// @ExecutionModes({on_single_node="/main_menu/format/Code/DetailsText"})

// Create a file that contains this node source code text
File file = File.createTempFile( "format-code", "" )
file.text = node.details.plain

// Create the command line that convert this source code file to html
command =
    '"C:\\Program Files\\highlight\\highlight.exe"' +
    ' "' + file.absolutePath + '"' +
    ' --syntax lisp' +
    ' --font "Source Code Pro" --font-size 12 --style edit-xcode' +
    ' -O html -I -u UTF-8 -f --enclose-pre --inline-css'

// Get the output of this conversion
html = command.execute().text

// Tweak it a little for freeplane
html = html.replaceAll( /background-color.*?;/, "" )
html = html.replaceAll( "&apos;", "'" )
html = html.replaceAll( />\s+<\/pre>/, "></pre>" )
html = "<html><body>" + html.trim() + "</body></html>"

// Replace the original node content by this html content, and format the node
node.details = html
node.style.maxNodeWidth = 2000
if( ! node.icons.contains( "Code" ) ) node.icons.add( "Code" )
