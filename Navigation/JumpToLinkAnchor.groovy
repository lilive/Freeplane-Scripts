// @ExecutionModes({on_single_node="/main_menu/navigate"})

import org.freeplane.features.link.LinkController
import org.freeplane.features.link.mindmapmode.MLinkController

// Get the ID of the link anchor node
def anchorID = ((MLinkController)(LinkController.getController())).getAnchorID()
if( ! anchorID ){
    c.setStatusInfo( "standard", "Link anchor is not defined !", "messagebox_warning" )
    return
}

// URI of the map that contains the link anchor node
def anchorMapURI = anchorID.substring( 0, anchorID.indexOf("#") )

// URI of the current map
def map = node.mindMap
def mapFile = node.mindMap.file
def mapURI = mapFile.toURI().toString()

// Do nothing if we are not in the map that contains the link anchor
if( ! anchorMapURI.equals( mapURI ) ) {
    c.setStatusInfo( "standard", "The link anchor is not in this map !", "messagebox_warning" )
    return
}

// Jump to the link anchor node
def id = anchorID.substring( anchorID.indexOf("#") + 1 )
def n = node.map.node( id )
c.select( n )
c.centerOnNode( n )
c.setStatusInfo( "standard", "The link anchor is now selected", "button_ok" )
