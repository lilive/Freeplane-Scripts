// @ExecutionModes({on_single_node="/main_menu/filter/FindSimilarNodes"})

// Filter the map to keep only the nodes that contains the text
// of the selected node.



//*** Parameters *****************************************************

// Write true to show ancestors after filtering, or false instead
def showAncestors = true

// Write true to show descendants after filtering, or false instead
def showDescendants = false

// Write true to unfold all the results
def unfoldAllMap = true

//********************************************************************




def text = node.plainText.trim().toLowerCase()
node.map.filter( showAncestors, showDescendants ){ it.plainText.toLowerCase().contains( text ) }
if( unfoldAllMap ) unfold( node.map.root )
c.centerOnNode( node )

def unfold( node ){
    if( node.isVisible() ){
        node.folded = false
        node.children.each{ unfold( it ) }
    }
}
