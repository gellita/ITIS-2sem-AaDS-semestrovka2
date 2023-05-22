package RBTree;

public class RedBlackTreeRealization {
    static final boolean RED = false;
    static final boolean BLACK = true;
    Node root;
    private Exception IOException;
    long addCount = 0;
    long deleteCount = 0;
    long containsCount = 0;


    public boolean searchNode(int key) {
        containsCount = 0;
        Node node = root;
        while (node != null) {
            if (key == node.data) {
                containsCount++;
                return true;


            } else if (key < node.data) {
                containsCount++;
                node = node.left;
            } else {
                containsCount++;
                node = node.right;
            }

        }
        return false;
    }

    //---вставка---
    public boolean insertNode(int key) throws Exception {
        addCount = 0;
        Node node = root;
        Node parent = null;

        while (node != null) {
            addCount++;
            parent = node;
            if (key < node.data) {
                node = node.left;
                addCount++;
            } else if (key > node.data) {
                node = node.right;
                addCount++;
            } else {
                addCount++;
                throw new IllegalArgumentException("BST already contains a node with key " + key);
            }
        }

        //--вставка нового узла--
        Node newNode = new Node(key);
        addCount++;
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
            addCount++;
        } else if (key < parent.data) {
            parent.left = newNode;
            addCount++;
        } else {
            parent.right = newNode;
            addCount++;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
        return false;
    }
    private void fixRedBlackPropertiesAfterInsert(Node node) throws Exception {
        Node parent = node.parent;

        // 1: родитель нулевой, конец рекурсии
        if (parent == null) {
            addCount++;
            return;
        }

        // Узел родителя черный
        if (parent.color == BLACK) {
            addCount++;
            return;
        }


        Node grandparent = parent.parent;
        // 2: Если прародителя нет, то значит текущий узел - корневой, меняем цвет на черный.
        if (grandparent == null) {
            addCount++;
            parent.color = BLACK;
            return;
        }

        // получить "дядю" узла
        Node uncle = getUncle(parent);

        // Случай 3: Дядя красный -> перекрасить родителя,прародителя и дядю
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;
            addCount++;

            // Рекурсивный вызов прародителя, который теперь красный.
            // Это может быть root или красный родитель, и в этом случае нам нужно исправить больше...
            fixRedBlackPropertiesAfterInsert(grandparent);
        }



        // Родитель левый ребенок прародителя
        else if (parent == grandparent.left) {
            addCount++;

            if (node == parent.right) {
                rotateLeft(parent);
                parent = node;
                addCount++;
            }

            rotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }

        else {
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
                addCount++;
            }

            rotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    private Node getUncle(Node parent) throws Exception {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            addCount++;
            return grandparent.right;
        } else if (grandparent.right == parent) {
            addCount++;
            return grandparent.left;
        }else {
            throw IOException;
        }
    }

    //Удаление-----------


    public void deleteNode(int key) {
        deleteCount = 0;
        Node node = root;

        // Поиск узла для удаления
        while (node != null && node.data != key) {
            deleteCount++;
            // Проходим по дереву влево или вправо в зависимости от величины узла
            if (key < node.data) {
                deleteCount++;
                node = node.left;
            } else {
                deleteCount++;
                node = node.right;
            }
        }

        // Если узел не найден
        if (node == null) {
            deleteCount++;
            return;
        }


        Node movedUpNode;
        boolean deletedNodeColor;

        // Узел не имеет детей или имеет одного ребенка
        if (node.left == null || node.right == null) {
            deleteCount++;
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }

        // Узел имеет двух детей
        else {
            deleteCount++;
            // Находим минимальный узел правого поддерева ("непоследовательный преемник" текущего узла)
            Node inOrderSuccessor = findMinimum(node.right);
            node.data = inOrderSuccessor.data;
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);
            deleteCount++;
            // Удаляем временный NIL-узел
            if (movedUpNode.getClass() == NilNode.class) {
                deleteCount++;
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }
    }

    private Node deleteNodeWithZeroOrOneChild(Node node) {
        // У узла есть ТОЛЬКО левый дочерний элемент --> заменить его левым дочерним элементом
        if (node.left != null) {
            deleteCount++;
            replaceParentsChild(node.parent, node, node.left);
            return node.left;
        }else if (node.right != null) { // У узла есть ТОЛЬКО правый дочерний элемент --> заменить его правым дочерним элементом
            deleteCount++;
            replaceParentsChild(node.parent, node, node.right);
            return node.right;
        } else {
            // Узла нет детей, если узел красный - удалить, если черный - заменить его NIL узлом
            deleteCount++;
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }

    //Поиск минимального узла
    private Node findMinimum(Node node) {
        while (node.left != null) {
            deleteCount++;
            node = node.left;
        }
        return node;
    }

    private void fixRedBlackPropertiesAfterDelete(Node node) {
        // 1: Исследуемый узел является корневым, конец рекурсии
        if (node == root) {
            deleteCount++;
            return;
        }

        Node sibling = getSibling(node);

        // 2: Брат узла красного цвета
        if (sibling.color == RED) {
            deleteCount++;
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        // 3 и 4: брат узла черного цвета и имеет двоих детей
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            deleteCount++;
            sibling.color = RED;
            //  3: Черный брат с двумя черными детьми + красный родитель
            if (node.parent.color == RED) {
                deleteCount++;
                node.parent.color = BLACK;
            }else {  // Случай 4: Черный брат с двумя черными детьми + черный родитель
                deleteCount++;
                fixRedBlackPropertiesAfterDelete(node.parent);
            }
        }else { // 5 и 6: Черный родной брат, по крайней мере, с одним красным дочерним элементом
            deleteCount++;
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

    private void handleRedSibling(Node node, Node sibling) {
        // Смена цвета
        sibling.color = BLACK;
        node.parent.color = RED;

        // Поворот
        if (node == node.parent.left) {
            deleteCount++;
            rotateLeft(node.parent);
        } else {
            deleteCount++;
            rotateRight(node.parent);
        }
    }

    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        //Черный брат с хотя бы одним красным ребенком и черным племянником
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            deleteCount++;
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            deleteCount++;
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        //Черный брат с хотя бы одним красным ребенком и красным племянником
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild) {
            deleteCount++;
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            deleteCount++;
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }


    //Получить брата узла
    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            deleteCount++;
            return parent.right;
        } else if (node == parent.right) {
            deleteCount++;
            return parent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }


    //Проверка того, черный ли узел
    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    //Методы помощи для удаления и вставки-------
    //Метод поворта по часовой
    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            deleteCount++;
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }
    //Поворот против часовой
    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            deleteCount++;
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }
    //Метод смены детей ролительского узла
    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            deleteCount++;
            root = newChild;
        } else if (parent.left == oldChild) {
            deleteCount++;
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            deleteCount++;
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            deleteCount++;
            newChild.parent = parent;
        }
    }

    //Обход дерева
    public void preorderTraversal(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }

    // -- For toString() -----------------------------------------------------------------------------

    protected void appendNodeToString(Node node, StringBuilder builder) {
        builder.append(node.data).append(node.color == RED ? "[R]" : "[B]");
    }
    public long getAddCount(){
        return addCount;
    }
    public long getContainsCount(){
        return containsCount;
    }
    public long getDeleteCount(){
        return deleteCount;
    }
}


