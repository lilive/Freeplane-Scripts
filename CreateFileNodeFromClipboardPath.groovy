// @ExecutionModes({on_selected_node="/main_menu/insert/links"})

// A appeler si un chemin vers un fichier est dans le presse-papier.
// Crée un noeud enfant dont le texte est le nom du fichier et qui contient
// un lien vers le fichier.
// Author: lilive
// Licence: WTFPL2

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

path = Toolkit
    .getDefaultToolkit()
    .getSystemClipboard()
    .getContents( null )
    .getTransferData( DataFlavor.stringFlavor )
file = new File( path )

if( file.exists() ){
    n = node.createChild( file.name )
    n.link.uri = file.toURI()
    c.setStatusInfo( "standard", "OK !", "button_ok" )
} else {
    c.setStatusInfo( "standard", "Pas de chemin valide vers un fichier dans le presse-papier !", "button_cancel" )
}

