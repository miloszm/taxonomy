# taxonomy

object Taxonomy holds root node and repository of nodes forming a tree

the tree can be serialised and deserialised to and from the following CSV format:

        categories
        ,shows
        ,,theatre
        ,,films
        ,,,chinese,Cinese
        ,,,comedy
        ,,,action
        ,music
        ,,jazz
        ,,pop
        ,,rock
        ,restaurants,Restaurantes
        ,,chinese,Cinese
        ,,french
        ,,italian

placement in cells from left to right reflects parent-child relationship within a tree,
e.g., theatre is a child of shows, while music is a sibling of shows, and jazz pop and rock are also siblings

tags are listed in cells following the node name cell

