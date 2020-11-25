// @ExecutionModes({on_single_node="/main_menu/navigate"})

/*
Permet de chercher un noeud parmi un ensemble de noeuds frères.
- Plie le noeud sélectionné
- Sélectionne le noeud frère précédent
- Déplie-le au même niveau que l'était le noeud sélectionné
Todo: si un des noeuds n'a pas d'enfants, tous les noeuds suivant qui
seront explorés ne seront pas dépliés. Il faudrait remédier à cela.
 */

def getUnfoldedDepth( n ){
    if( n.folded || ! n.children || n.children.size() == 0 ) return 0
    return 1 + ( n.children ? n.children.collect{ getUnfoldedDepth( it ) }.max() : 0 )
}
def fold( n ){
    n.children.each{ fold( it ) }
    n.folded = true
}
def foldAtLevel( n, depth ){
    if( depth <= 0 ){
        fold( n )
    } else {
        n.folded = false
        n.children.each{  foldAtLevel( it, depth - 1 ) }
    }
}

def parent = node.parent
if( ! parent ) return

def i = parent.getChildPosition( node )
i --
def siblings = parent.children
if( i < 0 ) return
def n = siblings[ i ]

def depth = getUnfoldedDepth( node )
fold( node )
foldAtLevel( n, depth )
c.select( n )
c.centerOnNode( n )
