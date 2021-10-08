package SurveyManager;

//Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
// New Think Tank




class BtNode {

    int key;
    String name;

    BtNode leftChild;
    BtNode rightChild;

    BtNode(int key, String name) {

        this.key = key;
        this.name = name;

    }

    public String toString() {

        return name + " has the key " + key;

        /*
         * return name + " has the key " + key + "\nLeft Child: " + leftChild +
         * "\nRight Child: " + rightChild + "\n";
         */

    }

}

