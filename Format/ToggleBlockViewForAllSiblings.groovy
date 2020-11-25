// @ExecutionModes({on_single_node="/menu_bar/format/menu_coreFormat/NodeWidth"})
import org.freeplane.view.swing.map.NodeView

// Source: https://freeplane.sourceforge.io/wiki/index.php/Scripts_collection#toggle_block_view_for_all_siblings
// Author: Nnamdi Kohn

//
// configuration
//

// set default node identifier
def strIdentifier = "" // e.g. "[" for regarding only nodes with cores starting with "["

// width raster for the maximum width to get snatched
def width_grid = 20

// offset to be added to maximum node width
def width_offset = 10

//
// local variables
//

// get map zoom factor
def zoomfactor = c.getZoom()

// maximum width (with default value)
def maxwidth = 0

// node with maximum width (with default value)
def maxnode = node

// save current node object
def current_node_style = node.style.name

// node view
def nodeView = 0

// setting for regarding style setting
def bRegardStyleSetting = 1

// get parent node from selected node
def nodeParent = node.getParent()

// widthcompare
def widthcompare = 0

// get current width of component
def width = 0

// get list of siblings
lstSiblings = nodeParent.getChildren()

// if there are more than one nodes selected ...
if( c.selecteds.size() > 1 ) {

    // list of siblings will be all selected nodes
    lstSiblings = c.selecteds

    // don't regard the node's styles
    bRegardStyleSetting = 0

}

// set default for marker
def bLeaveFunctionHereafter = 0

//
// check if any relevant node has minimum width setting and reset setting
//

// take every sibling and ...
lstSiblings.each{

    // only regard nodes with fitting style ...
    if( it.style.name == current_node_style || bRegardStyleSetting == 0 ){

        // if any minimum width is set ...
        if( it.style.getMinNodeWidth() > 1 ){

            // if node start character specifics are fitting ...
            if( it.getPlainText().startsWith( strIdentifier ) ){

                // set marker
                bLeaveFunctionHereafter = 1

                // reset minimum node width
                it.style.setMinNodeWidth( -1 )

            }
        }
    }
}

// if marker was previously set ...
if( bLeaveFunctionHereafter == 0 ){

    //
    // determine maximum current node width of all siblings
    //

    // take every sibling and ...
    lstSiblings.each{

        // only regard nodes with fitting style ...
        if( it.style.name == current_node_style || bRegardStyleSetting == 0 ){

            // get nodeView
            nodeView = it.delegate.viewers.find() {it instanceof NodeView}

            // get current width of component
            width = nodeView?.mainView?.width

            // if its current width is the biggest so far ...
            if( width > maxwidth ){

                // if node start character specifics are fitting ...
                if( it.getPlainText().startsWith( strIdentifier ) ){

                    // update maximum width variable with current node width
                    maxwidth = width

                    // set max width node
                    maxnode = it

                }
            }
        }
    }

    // jump to node with maximum width
    //c.select( maxnode )

    //
    // set minimum width value for all siblibngs
    //

    // set starting width
    widthcompare = width_grid

    // fit maxwidth to raster
    while( maxwidth.div( zoomfactor ) + width_offset > widthcompare ){

        // increment width compare value
        widthcompare += width_grid
    }

    // take every sibling and ...
    lstSiblings.each{

        // only regard nodes with fitting style ...
        if( it.style.name == current_node_style || bRegardStyleSetting == 0 ){

            // if node start character specifics are fitting ...
            if( it.getPlainText().startsWith( strIdentifier ) ){

                // set its minimum width to the maximum current plus delta
                it.style.minNodeWidth = widthcompare

            }
        }
    }
}

// printout status
c.statusInfo = "zoom: $zoomfactor ; maxwidth: $maxwidth ; width: $width ; widthcompare: $widthcompare"
