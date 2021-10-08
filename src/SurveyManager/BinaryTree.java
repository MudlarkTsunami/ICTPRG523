package SurveyManager;

public class BinaryTree
{

    BtNode root;
    String output;

    public void addBtNode(int key, String name)
    {

        // Create a new BtNode and initialize it

        BtNode newBtNode = new BtNode(key, name);

        // If there is no root this becomes root

        if (root == null)
        {

            root = newBtNode;

        } else
        {

            // Set root as the BtNode we will start
            // with as we traverse the tree

            BtNode focusBtNode = root;

            // Future parent for our new BtNode

            BtNode parent;

            while (true)
            {

                // root is the top parent so we start
                // there

                parent = focusBtNode;

                // Check if the new BtNode should go on
                // the left side of the parent BtNode

                if (key < focusBtNode.key)
                {

                    // Switch focus to the left child

                    focusBtNode = focusBtNode.leftChild;

                    // If the left child has no children

                    if (focusBtNode == null)
                    {

                        // then place the new BtNode on the left of it

                        parent.leftChild = newBtNode;
                        return; // All Done

                    }

                } else
                { // If we get here put the BtNode on the right

                    focusBtNode = focusBtNode.rightChild;

                    // If the right child has no children

                    if (focusBtNode == null)
                    {

                        // then place the new BtNode on the right of it

                        parent.rightChild = newBtNode;
                        return; // All Done

                    }

                }

            }
        }

    }

    // All BtNodes are visited in ascending order
    // Recursion is used to go to one BtNode and
    // then go to its child BtNodes and so forth

    public void inOrderTraverseTree(BtNode focusBtNode)
    {

        if (focusBtNode != null)
        {

            // Traverse the left BtNode

            inOrderTraverseTree(focusBtNode.leftChild);

            // Visit the currently focused on BtNode

            //System.out.println(focusBtNode);
            output = output + " " + focusBtNode.key + "-" + focusBtNode.name + ",";

            // Traverse the right BtNode

            inOrderTraverseTree(focusBtNode.rightChild);


        }

    }





    public void preorderTraverseTree(BtNode focusBtNode) {

        if (focusBtNode != null) {

            //System.out.println(focusBtNode);
            output = output + " " + focusBtNode.key + "-" + focusBtNode.name + ",";

            preorderTraverseTree(focusBtNode.leftChild);
            preorderTraverseTree(focusBtNode.rightChild);

        }

    }

    public void postOrderTraverseTree(BtNode focusBtNode) {

        if (focusBtNode != null) {

            postOrderTraverseTree(focusBtNode.leftChild);
            postOrderTraverseTree(focusBtNode.rightChild);

            //System.out.println(focusBtNode);
            output = output + " " + focusBtNode.key + "-" + focusBtNode.name + ",";

        }

    }

    public BtNode findBtNode(int key) {

        // Start at the top of the tree

        BtNode focusBtNode = root;

        // While we haven't found the BtNode
        // keep looking

        while (focusBtNode.key != key) {

            // If we should search to the left

            if (key < focusBtNode.key) {

                // Shift the focus BtNode to the left child

                focusBtNode = focusBtNode.leftChild;

            } else {

                // Shift the focus BtNode to the right child

                focusBtNode = focusBtNode.rightChild;

            }

            // The BtNode wasn't found

            if (focusBtNode == null)
                return null;

        }

        return focusBtNode;

    }

    public static void main(String[] args) {

        BinaryTree theTree = new BinaryTree();

        theTree.addBtNode(50, "Boss");

        theTree.addBtNode(25, "Vice President");

        theTree.addBtNode(15, "Office Manager");

        theTree.addBtNode(30, "Secretary");

        theTree.addBtNode(75, "Sales Manager");

        theTree.addBtNode(85, "Salesman 1");

        // Different ways to traverse binary trees

        // theTree.inOrderTraverseTree(theTree.root);

        // theTree.preorderTraverseTree(theTree.root);

        // theTree.postOrderTraverseTree(theTree.root);

        // Find the BtNode with key 75

        System.out.println("\nBtNode with the key 75");

        System.out.println(theTree.findBtNode(75));

    }
}

