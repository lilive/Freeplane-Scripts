// @ExecutionModes({on_single_node="/main_menu/navigate/Bookmarks"})

// Use this script to bookmark the currently opened map
// author: lilive

// The bookmark identifier for this map
id = "3"

paths = [:]
storagePath = c.getUserDirectory().toString() + File.separator + 'lilive_bookmarked_maps.json'
file = new File( storagePath )
if( file.exists() ) paths = new groovy.json.JsonSlurper().parseText( file.text )
paths[ id ] = node.map.file.absolutePath
json = groovy.json.JsonOutput.toJson( paths )
file.write( json )
