// @ExecutionModes({on_selected_node="/menu_bar/format/menu_coreFormat"})

/*
Clean the HTML code in the node core.
Headings are converted to <p> tags.
Only basic tags are preserved, including the links.
Other tags are removed.
Configure the behaviour with the toParagraph and toKeep lists.

The script need the JSoup library.
Copy jsoup-X.X.X.jar in the Freeplane home_dir/lib folder.
The library is available here: https://jsoup.org/

Author: lilive
Licence: WTFPL 2
*/

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.jsoup.safety.Cleaner
import org.jsoup.safety.Whitelist

// tags to convert to <p> tags
toParagraph = [ "h1", "h2", "h3", "h4", "h5", "h6" ]
// tags to keep
toKeep = [ "br", "p", "a", "b", "i", "em", "strong", "u", "ul", "ol", "li" ]

Document doc = Jsoup.parse( node.text )

// Convert some tags to <p> tags
toParagraph.each{
    Elements elements= doc.getElementsByTag( it )
    elements.tagName( "p" )
}

// Remove all tags, except the toKeep list,
// and all attributes, except the href attribute of the <a> tags
Whitelist whitelist = Whitelist.none()
toKeep.each{ whitelist.addTags( it) }
whitelist.addAttributes( "a", "href" )
Cleaner cleaner = new Cleaner( whitelist )
doc = cleaner.clean( doc )

node.text = doc.html()
