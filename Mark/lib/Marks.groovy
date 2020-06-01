import org.freeplane.plugin.script.proxy.ScriptUtils

def static store( n )
{
	def node = ScriptUtils.node()
	def c = ScriptUtils.c()
	
    node.map.storage['Marks_stored_node_id'] = n.id
    c.statusInfo = 'Node marked.'
}

def static linkCurrentToStored()
{
	def node = ScriptUtils.node()
    def c = ScriptUtils.c()
    
    def id = node.map.storage['Marks_stored_node_id']
    if( id )
    {
        def r = c.find{ id.equals( it.getId() ) }
        if( !r.isEmpty() )
        {
        		def target = r[0]
        		node.link.node = target
        }
        else
        {
        		c.statusInfo = "The previously marked node doesn't exist anymore."
        }
        	
    }
    else
    {
    		c.statusInfo = 'No previously marked node.'
    }
}

def static linkStoredToCurrent()
{
	def node = ScriptUtils.node()
    def c = ScriptUtils.c()
    
    def id = node.map.storage['Marks_stored_node_id']
    if( id )
    {
        def r = c.find{ id.equals( it.getId() ) }
        if( !r.isEmpty() )
        {
        		def target = r[0]
        		target.link.node = node
        		c.statusInfo = "The marked node is now linked to this node."
        }
        else
        {
        		c.statusInfo = "The previously marked node doesn't exist anymore."
        }
        	
    }
    else
    {
    		c.statusInfo = 'No previously marked node.'
    }
}

def static gotoStored( boolean toggleMarkAndCurrent )
{
	def node = ScriptUtils.node()
    def c = ScriptUtils.c()
	
    def id = node.map.storage['Marks_stored_node_id']
    if( id )
    {
        def r = c.find{ id.equals( it.getId() ) }
        if( !r.isEmpty() )
        {
        		def target = r[0]
            c.select( target )
            if( toggleMarkAndCurrent )
			{
					store( node )
			}
			c.statusInfo = "Jump to previously marked node."
        }
        else
        {
        		c.statusInfo = "The previously marked node doesn't exist anymore."
        }
    }
    else
    {
    		c.statusInfo = 'No previously marked node.'
    }
}
