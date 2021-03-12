// @ExecutionModes({on_single_node="/main_menu/filter/FindSimilarNodes"})

// Filter the map to keep only the nodes that contains at least a
// word similar to one of the words in the selected node.
// "Similar" words are words that begin with the same characters.



//*** Parameters *****************************************************

// The minimal length (number of characters) for a word to be part of the search
def wordMinLength = 4

// Write true to show ancestors after filtering, or false instead
def showAncestors = true

// Write true to show descendants after filtering, or false instead
def showDescendants = false

// Write true to unfold all the results
def unfoldAllMap = true

//********************************************************************




def text = node.plainText.trim().toLowerCase()
def words = text.split( "\\s" )
words = words.findAll{ it.length() >= wordMinLength }
words = words.collect{ "\\b${it}" }
text = words.join( "|" )
def pattern = ~/$text/
node.map.filter( showAncestors, showDescendants ){ ( it.plainText.toLowerCase() =~ pattern ).find() }
if( unfoldAllMap ) unfold( node.map.root )
c.centerOnNode( node )

def unfold( node ){
    if( node.isVisible() ){
        node.folded = false
        node.children.each{ unfold( it ) }
    }
}
