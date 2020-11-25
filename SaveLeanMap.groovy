// @ExecutionModes({ON_SINGLE_NODE="/main_menu/file/SaveAsAction"})

// Author: Edo Frohlich

// get initial values
def save_modification_times = config.properties['save_modification_times']
def save_folding = config.properties['save_folding']
def save_last_visited_node = config.properties['save_last_visited_node']

// set "lean" values
config.properties['save_modification_times'] = 'false'
config.properties['save_folding'] = 'never_save_folding' //never_save_folding  , save_folding_if_map_is_changed
config.properties['save_last_visited_node'] = 'false'

//saves map
node.map.saved = false
def success = node.map.save( true )

//return to initial values
config.properties['save_modification_times'] = save_modification_times
config.properties['save_folding'] = save_folding
config.properties['save_last_visited_node'] = save_last_visited_node

// reports result
ui.informationMessage( success ? "Map was saved in a lean format" : "Map couldn't get saved" )
