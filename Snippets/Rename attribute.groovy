// Rename an attribute in each node of a map

root = node.map.root
root.findAll().each{
    attributes = it.getAttributes()
    names = attributes.names.eachWithIndex{
        name, idx ->
        if( name == 'catégorie' ){
            vallue = attributes.get( idx )
            attributes.set( idx, 'tag', vallue )
        }
    }
}
