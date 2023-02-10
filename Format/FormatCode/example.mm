<map version="freeplane 1.9.13">
<!--To view this file, download free mind mapping software Freeplane from https://www.freeplane.org -->
<node TEXT="Map" LOCALIZED_STYLE_REF="AutomaticLayout.level.root" ID="ID_1090958577" VGAP_QUANTITY="2 pt">
<hook NAME="accessories/plugins/AutomaticLayout.properties" VALUE="ALL"/>
<font BOLD="true"/>
<attribute_layout NAME_WIDTH="80 pt" VALUE_WIDTH="120 pt"/>
<hook NAME="MapStyle" background="#ffffff" zoom="0.8">
    <properties show_icon_for_attributes="true" edgeColorConfiguration="#353535ff,#353535ff,#353535ff,#353535ff,#353535ff" show_note_icons="true" fit_to_viewport="false" associatedTemplateLocation="file:/C:/Users/devine/Documents/docs/Connaissances%20techniques.mm"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node" ID="ID_412273920" STYLE="oval" UNIFORM_SHAPE="true" VGAP_QUANTITY="24 pt">
<font SIZE="24"/>
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right" ID="ID_1646925444" STYLE="bubble">
<stylenode LOCALIZED_TEXT="default" ID="ID_915081904" ICON_SIZE="14 pt" FORMAT_AS_HYPERLINK="false" MAX_WIDTH="600 px" MIN_WIDTH="0 cm" COLOR="#000000" BACKGROUND_COLOR="#cccccc" STYLE="rectangle" SHAPE_HORIZONTAL_MARGIN="6 pt" SHAPE_VERTICAL_MARGIN="4 pt" NUMBERED="false" FORMAT="STANDARD_FORMAT" TEXT_ALIGN="DEFAULT" BORDER_WIDTH_LIKE_EDGE="false" BORDER_WIDTH="0.1 px" BORDER_COLOR_LIKE_EDGE="false" BORDER_COLOR="#000000" BORDER_DASH_LIKE_EDGE="false" BORDER_DASH="SOLID" VGAP_QUANTITY="2 pt" CHILD_NODES_ALIGNMENT="BY_CENTER">
<arrowlink SHAPE="CUBIC_CURVE" COLOR="#000000" WIDTH="2" TRANSPARENCY="100" DASH="" FONT_SIZE="9" FONT_FAMILY="Raleway" DESTINATION="ID_915081904" STARTARROW="DEFAULT" ENDARROW="NONE"/>
<font NAME="Candara" SIZE="14" BOLD="false" STRIKETHROUGH="false" ITALIC="false"/>
<edge STYLE="horizontal" COLOR="#000000" WIDTH="5" DASH="SOLID"/>
<richcontent CONTENT-TYPE="plain/html" TYPE="DETAILS"/>
<richcontent TYPE="NOTE" CONTENT-TYPE="plain/html"/>
<hook NAME="NodeCss">a {
    color: #D40101;
}</hook>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details" ID="ID_1282721560" BACKGROUND_COLOR="#999999" BACKGROUND_ALPHA="0" BORDER_COLOR="#cccccc" BORDER_COLOR_ALPHA="0">
<font NAME="Candara"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.attributes" ID="ID_259572124">
<font SIZE="9"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.note" ID="ID_201716257"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating" ID="ID_824662518">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.selection" ID="ID_1844855534" BACKGROUND_COLOR="#ff6666" BORDER_COLOR_LIKE_EDGE="false" BORDER_COLOR="#000000" BORDER_COLOR_ALPHA="175"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right" STYLE="bubble">
<stylenode TEXT="Code" ID="ID_1414614110" BACKGROUND_COLOR="#ffffff" STYLE="rectangle" SHAPE_HORIZONTAL_MARGIN="4 pt" MAX_WIDTH="2000 px" BORDER_WIDTH="1 px" BORDER_COLOR="#192441" BORDER_COLOR_ALPHA="75">
<font NAME="Source Code Pro" SIZE="12"/>
</stylenode>
<stylenode TEXT="Raccourci clavier" ID="ID_10796926" BACKGROUND_COLOR="#ffffff" BACKGROUND_ALPHA="0" STYLE="rectangle" SHAPE_HORIZONTAL_MARGIN="4 pt" MAX_WIDTH="2000 px" BORDER_WIDTH="2 px" BORDER_COLOR="#000000" BORDER_COLOR_ALPHA="129" BORDER_DASH="CLOSE_DOTS">
<font NAME="Source Code Pro" SIZE="12" BOLD="false" ITALIC="true"/>
</stylenode>
<stylenode TEXT="Dossier" ID="ID_597351580" BORDER_WIDTH="0 px" BORDER_COLOR="#4c55a6" BORDER_COLOR_ALPHA="0" COLOR="#292941" BACKGROUND_COLOR="#bacaf1">
<font NAME="Candara" BOLD="true"/>
</stylenode>
<stylenode TEXT="Image" ID="ID_554330617" COLOR="#000000" BACKGROUND_COLOR="#ffffff" BACKGROUND_ALPHA="0" STYLE="bubble" SHAPE_HORIZONTAL_MARGIN="0 pt" SHAPE_VERTICAL_MARGIN="0 pt" MAX_WIDTH="2000 px" BORDER_WIDTH="0 px" BORDER_COLOR_LIKE_EDGE="false" BORDER_COLOR="#808080" BORDER_COLOR_ALPHA="0">
<font NAME="Candara" SIZE="14"/>
<edge STYLE="horizontal" COLOR="#192441" WIDTH="thin"/>
</stylenode>
<stylenode TEXT="Output" ID="ID_1927518522" BACKGROUND_COLOR="#666666" BACKGROUND_ALPHA="74" STYLE="rectangle" SHAPE_HORIZONTAL_MARGIN="6 pt" SHAPE_VERTICAL_MARGIN="6 pt" MAX_WIDTH="2000 px" BORDER_WIDTH="1 px" BORDER_COLOR="#192441" BORDER_COLOR_ALPHA="75">
<icon BUILTIN="forward"/>
<font NAME="Source Code Pro" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important" ID="ID_1955688491" COLOR="#ffffff" BACKGROUND_COLOR="#bf616a" BORDER_COLOR="#bf616a">
<icon BUILTIN="yes"/>
<font NAME="Ubuntu" SIZE="12" BOLD="true"/>
<edge COLOR="#bf616a"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right" STYLE="bubble">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" ID="ID_729316690" COLOR="#000000">
<font SIZE="18"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<node STYLE_REF="Code" POSITION="right" ID="ID_1220136703" BACKGROUND_COLOR="#ffcccc" MAX_WIDTH="920 px" MIN_WIDTH="920 px"><richcontent TYPE="NODE">

<html>
  <head>
    
  </head>
  <body>
    <pre style="color: #000000; font-size: 12pt; font-family: Source Code Pro"><span style="color: #8f0055"><font color="#8f0055">void</font></span> <span style="color: #8f0055"><font color="#8f0055">FluidMesh</font></span><span style="color: #000000"><font color="#000000">::</font></span><font color="#000000"><span style="color: #000000">setup</span><span style="color: #000000">(</span></font> <span style="color: #8f0055"><font color="#8f0055">const</font></span> ofTexture <span style="color: #000000"><font color="#000000">&amp;</font></span> pattern<span style="color: #000000"><font color="#000000">,</font></span> <span style="color: #8f0055"><font color="#8f0055">float</font></span> cellSize <span style="color: #000000"><font color="#000000">)</font></span>
<span style="color: #000000"><font color="#000000">{</font></span>
    <span style="color: #000000"><font color="#000000">setup</font></span><font color="#000000"><span style="color: #000000">(</span></font> pattern<span style="color: #000000"><font color="#000000">,</font></span> <span style="color: #2300ff"><font color="#2300ff">0</font></span><span style="color: #000000"><font color="#000000">.</font></span>f<span style="color: #000000"><font color="#000000">,</font></span> <span style="color: #2300ff"><font color="#2300ff">0</font></span><span style="color: #000000"><font color="#000000">.</font></span>f<span style="color: #000000"><font color="#000000">,</font></span> cellSize <span style="color: #000000"><font color="#000000">) ;</font></span>
<span style="color: #000000"><font color="#000000">}</font></span></pre>
  </body>
</html>

</richcontent>
<attribute_layout NAME_WIDTH="80 pt" VALUE_WIDTH="120 pt"/>
</node>
<node TEXT="A node with code in details" POSITION="right" ID="ID_1323692694" MAX_WIDTH="920 px" MIN_WIDTH="920 px" BACKGROUND_COLOR="#ff9999" VSHIFT_QUANTITY="7.8 pt">
<icon BUILTIN="Code"/>
<attribute_layout NAME_WIDTH="80 pt" VALUE_WIDTH="120 pt"/>
<richcontent CONTENT-TYPE="xml/" TYPE="DETAILS">
<html>
  <head>
    
  </head>
  <body>
    <pre style="color: #000000; font-size: 12pt; font-family: Source Code Pro"><span style="color: #8f0055"><font color="#8f0055">while</font></span> choosing<span style="color: #000000"><font color="#000000">:</font></span>
    text <span style="color: #000000"><font color="#000000">=</font></span> <span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">\n</font></span><span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">join</span><span style="color: #000000">( [</span></font> base <span style="color: #000000"><font color="#000000">] *</font></span> l <span style="color: #000000"><font color="#000000">)</font></span>
    <span style="color: #8f0055"><font color="#8f0055">if</font></span> <span style="color: #8f0055"><font color="#8f0055">len</font></span><span style="color: #000000"><font color="#000000">(</font></span> text <span style="color: #000000"><font color="#000000">)</font></span> <span style="color: #8f0055"><font color="#8f0055">and</font></span> i <span style="color: #000000"><font color="#000000">&gt;=</font></span> <span style="color: #2300ff"><font color="#2300ff">0</font></span><span style="color: #000000"><font color="#000000">:</font></span> text <span style="color: #000000"><font color="#000000">+=</font></span> <span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">\n</font></span><span style="color: #c00000"><font color="#c00000">&quot;</font></span>
    text <span style="color: #000000"><font color="#000000">+=</font></span> <span style="color: #c00000"><font color="#c00000">&quot;&quot;</font></span><span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">join</span><span style="color: #000000">( [</span></font> s<span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">replace</span><span style="color: #000000">(</span></font> cr<span style="color: #000000"><font color="#000000">,</font></span> <span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">\n</font></span><span style="color: #c00000"><font color="#c00000">&quot;</font></span> <span style="color: #000000"><font color="#000000">)</font></span> <span style="color: #8f0055"><font color="#8f0055">for</font></span> s <span style="color: #8f0055"><font color="#8f0055">in</font></span> lorem<span style="color: #000000"><font color="#000000">[</font></span><span style="color: #2300ff"><font color="#2300ff">0</font></span><span style="color: #000000"><font color="#000000">:</font></span>i<span style="color: #000000"><font color="#000000">+</font></span><span style="color: #2300ff"><font color="#2300ff">1</font></span><span style="color: #000000"><font color="#000000">] ] )</font></span>
    os<span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">system</span><span style="color: #000000">(</span></font><span style="color: #c00000"><font color="#c00000">'cls'</font></span><span style="color: #000000"><font color="#000000">)</font></span>
    <span style="color: #8f0055"><font color="#8f0055">print</font></span><span style="color: #000000"><font color="#000000">(</font></span> text<span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">replace</span><span style="color: #000000">(</span></font> <span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">\n</font></span><span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">,</font></span> <span style="color: #c00000"><font color="#c00000">&quot;</font></span><span style="color: #000000"><font color="#000000">\n\n</font></span><span style="color: #c00000"><font color="#c00000">&quot;</font></span> <span style="color: #000000"><font color="#000000">) )</font></span>
    <span style="color: #8f0055"><font color="#8f0055">print</font></span><span style="color: #000000"><font color="#000000">()</font></span>
    <span style="color: #8f0055"><font color="#8f0055">print</font></span><span style="color: #000000"><font color="#000000">(</font></span> <span style="color: #c00000"><font color="#c00000">&quot;   ==== Arrows to change text amount - Enter to confirm ====&quot;</font></span> <span style="color: #000000"><font color="#000000">)</font></span>

    k <span style="color: #000000"><font color="#000000">=</font></span> <span style="color: #000000"><font color="#000000">readkey</font></span><font color="#000000"><span style="color: #000000">()</span></font>
    <span style="color: #8f0055"><font color="#8f0055">if</font></span> k<span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">eq</span><span style="color: #000000">(</span></font> Key<span style="color: #000000"><font color="#000000">.</font></span>UP <span style="color: #000000"><font color="#000000">):</font></span>
        <span style="color: #8f0055"><font color="#8f0055">if</font></span> i <span style="color: #000000"><font color="#000000">&gt;</font></span> lorem<span style="color: #000000"><font color="#000000">.</font></span><font color="#000000"><span style="color: #000000">index</span><span style="color: #000000">(</span></font> cr <span style="color: #000000"><font color="#000000">):</font></span>
            tp <span style="color: #000000"><font color="#000000">=</font></span> lorem<span style="color: #000000"><font color="#000000">[</font></span><span style="color: #2300ff"><font color="#2300ff">0</font></span><span style="color: #000000"><font color="#000000">:</font></span>i<span style="color: #000000"><font color="#000000">+</font></span><span style="color: #2300ff"><font color="#2300ff">1</font></span><span style="color: #000000"><font color="#000000">]</font></span>
            i <span style="color: #000000"><font color="#000000">=</font></span> <span style="color: #8f0055"><font color="#8f0055">len</font></span><span style="color: #000000"><font color="#000000">(</font></span>tp<span style="color: #000000"><font color="#000000">) -</font></span> <span style="color: #2300ff"><font color="#2300ff">1</font></span> <span style="color: #000000"><font color="#000000">-</font></span> tp<span style="color: #000000"><font color="#000000">[::-</font></span><span style="color: #2300ff"><font color="#2300ff">1</font></span><span style="color: #000000"><font color="#000000">].</font></span><font color="#000000"><span style="color: #000000">index</span><span style="color: #000000">(</span></font>cr<span style="color: #000000"><font color="#000000">) -</font></span> <span style="color: #2300ff"><font color="#2300ff">1</font></span></pre>
  </body>
</html>
</richcontent>
</node>
</node>
</map>
