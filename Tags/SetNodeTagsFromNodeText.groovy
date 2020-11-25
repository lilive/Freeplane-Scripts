// @ExecutionModes({on_selected_node="/main_menu/edit/tags"})

// Set the node hashtag attributes from the #xxxxx words found
// in this node text, details and note.
// Any existing hashtags attribute that do not refer to any text is deleted.
// Author: lilive
// Licence: WTFPL2

def tags = ( node.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
if( node.details ) tags += ( node.details.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
if( node.note ) tags += ( node.note.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
node.attributes.removeAll("hashtag")
tags.each{ node.attributes.add( "hashtag", it ) }
