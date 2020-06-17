// @ExecutionModes({on_selected_node="/main_menu/edit/tags"})

// Each #xxxxxx word in the node text, details or note is added
// as a new hashtag attribute in this node.
// Each hashtag attribute remains unique.

def tags = ( node.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
if( node.details ) tags += ( node.details.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
if( node.note ) tags += ( node.note.text =~ /#(\S+)/ ).findAll().collect{ it[1] }
def stored = node.attributes.getAll("hashtag")
tags.each{ if(!(it in stored)) node.attributes.add( "hashtag", it ) }
