// @ExecutionModes({on_single_node="/main_menu/format/cloud_shapes"})

import org.freeplane.features.mode.Controller
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.map.INodeChangeListener
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.NodeChangeEvent
import org.freeplane.plugin.script.proxy.ScriptUtils

class AutoCloudSelectionListener implements INodeSelectionListener, INodeChangeListener
{
    public void onDeselect(NodeModel node){
        def n = ScriptUtils.node()
        removeCloud( n )
    }

	public void onSelect(NodeModel node)
    {
        def n = ScriptUtils.node()
        putCloud( n )
    }
    
    public void nodeChanged(NodeChangeEvent event)
    {
        Object property = event.property
		NodeModel node = event.node
        if( ! NodeModel.NodeChangeType.FOLDING.equals(property) ) return
        def n = ScriptUtils.node()
        if( n.folded ) removeCloud( n )
        else putCloud( n )
    }

    static private putCloud( node )
    {
        if( ! node.folded && ! node.isLeaf() )
        { 
            node.cloud.enabled = true
            node.cloud.shape = "RECT"
            node.cloud.colorCode = "#FFFFFF22"
        }
    }

    static public removeCloud( node )
    {
        node.cloud.enabled = false
    }

}

def hasListener = false
def mapController = Controller.currentModeController.mapController
mapController.nodeSelectionListeners.findAll {
	it.getClass().name == AutoCloudSelectionListener.class.name
}.each {
	mapController.removeNodeSelectionListener(it)
    hasListener = true
}
mapController.nodeChangeListeners.findAll {
	it.getClass().name == AutoCloudSelectionListener.class.name
}.each {
	mapController.removeNodeChangeListener(it)
    hasListener = true
}

if( hasListener ){
    AutoCloudSelectionListener.removeCloud( node )
    ui.informationMessage( "Nuage automatique désactivé" )
} else {
    AutoCloudSelectionListener.putCloud( node )
    listener = new AutoCloudSelectionListener()
    mapController.addNodeSelectionListener( listener )
    mapController.addNodeChangeListener( listener )
    ui.informationMessage( "Nuage automatique activé" )
}
