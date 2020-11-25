// @ExecutionModes({on_single_node="/main_menu/navigate"})

// Jump to next clone
// Author: lilive
// Licence: WTFPL2

if( node.getCountNodesSharingContent() > 0 )
{
    // Get an orderer list of all the clones id
    def clones = node.getNodesSharingContent()
    def ids = []
    clones.each{ ids.add( it.id ) }
    ids.add( node.id )
    Collections.sort( ids )

    // Find the next clone id
    def index = ids.indexOf( node.id )
    index++
    if( index == ids.size() ) index = 0
    def id = ids[ index ]

    // Jump to it
    def clone = node.map.node( id )
    c.select( clone  )
    c.centerOnNode( clone )
}
else
{
    c.setStatusInfo( "standard", "Impossible to jump to next clone because this node has no clones !" )
}
