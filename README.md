# taxonomy

object Taxonomy holds root node as well as repository of nodes forming a tree
the tree can be serialised as CSV in the following format:

        categories,
        ,shows,
        ,,theatre,
        ,,films,
        ,,,chinese,Cinese,
        ,,,comedy,
        ,,,action,
        ,music,
        ,,jazz,
        ,,pop,
        ,,rock,
        ,restaurants,Restaurantes,
        ,,chinese,Cinese,
        ,,french,
        ,,italian

where placement in cells from left to right reflects parent child relationship within a tree
e.g., theatre is a child of shows, while music is a sibling of shows
node tags are listed in cells following the node name cell

