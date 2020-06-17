// @ExecutionModes({on_single_node="/main_menu/navigate/Bookmarks"})

// Use this script to load a bookmarked map
// author: lilive

// The bookmark identifier for this map
id = "4"

storagePath = c.getUserDirectory().toString() + File.separator + 'lilive_bookmarked_maps.json'
file = new File( storagePath )
if( ! file.exists() ) return

paths = new groovy.json.JsonSlurper().parseText( file.text )
path = paths?[ id ]
if( ! path ) return
c.mapLoader( path ).withView().getMindMap()

