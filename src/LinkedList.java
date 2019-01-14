//pedri017 park1394
public class LinkedList<T extends Comparable<T>> implements List<T>{
    private boolean isSorted=true;
    private Node<T> headNode;
    private Node<T> lastNode;
    private int entries=0;


    /* Add an element to end of the list. If element is null,
     it will NOT add it and return false.  Otherwise, it
     will add it and return true. Updates isSorted to false.
     */
    public boolean add(T element) {
        if(element!=null){
            Node<T> newEntry=new Node<T>(element);
            if (entries==0){       //if this is first element, head and last reference are the same
                headNode=newEntry;
                lastNode=newEntry;
            }
            else{       //if not first element, set as last node's next element and move pointer for last node
                lastNode.setNext(newEntry);
                lastNode=newEntry;
            }
            entries+=1;
            isSorted= false;
            return true;
        }
        else { return false; }  //does not add when element is null
    }
    /* Add an element at specific index. This method
     also shift the element currently at that position
     and any subsequent elements to the right  If element is null,
     or if  index is out-of-bounds (index < 0 or index >= entries),
     it will NOT add it and return false. Otherwise it will
     add it and return true. Also updates isSorted variable to false.
     */
    public boolean add(int index, T element) {
        Node<T> nodeBefore;
        Node<T> currentNode;
        Node<T> newEntry = new Node<T>(element);
        if (element!=null && index<entries && index >0) {
            nodeBefore=null;
            currentNode=headNode;
            for( int indexCount=0; indexCount<index; indexCount++){    //move up to desired index
                nodeBefore=currentNode;
                currentNode=currentNode.getNext();
            }
            nodeBefore.setNext(newEntry);
            newEntry.setNext(currentNode);
            isSorted=false;
            entries+=1;
            if (entries==index+1){
                lastNode=newEntry;
            }
            return true;
        }
        else if(index==0){
            newEntry.setNext(headNode);
            headNode=newEntry;
            entries+=1;
            return true;
        }
        else{ return false; }  //does not add when element is null or index out of bound
    }

    // Removes all elements from the list.
    public void clear() {
        headNode=null;
        lastNode=null;
        entries=0;
    }

    /* Return true if element is in the list and false
       otherwise. If isSorted is true, uses the ordering
       of the list to increase the efficiency of the search */
    public boolean contains(T element) {

        int count = 0;
        Node<T> compare = this.headNode;
        if (!this.isSorted) {
            while(count < this.entries) {
                if (compare.getData() == element) {
                    return true;
                }

                compare = compare.getNext();
                ++count;
            }

            return false;
        } else {
            if (this.headNode.getData().compareTo(element) <= 0 || this.lastNode.getData().compareTo(element) >= 0) {
                while(count < this.entries-1) {
                    if (compare.getData().compareTo(element) == 0)
                        return true;
                    if (compare.getData().compareTo(element) < 0 && compare.getNext().getData().compareTo(element) > 0)
                        return false;
                    compare = compare.getNext();
                    ++count;
                }
            }
            return false;
        }
    }

    /* Return the element at given index. If index is
       out-of-bounds, it will return null */
    public T get(int index) {
        int counter;
        if (index >=0 && index < entries) {
            Node <T> currentNode = headNode;
            for (counter = 0; counter < index; counter++) {
                currentNode=currentNode.getNext();
            }
            return (T)currentNode.getData();
        }
        return null;
    }


    /* Return the first index of element in the list. If element
     is null or not found in the list, returns -1. If isSorted is
     true, uses the ordering of the list to increase the efficiency
     of the search */
    public int indexOf(T element) {
        int count=0;
        Node<T> compare=headNode;
        if(element!=null) {
            if ( contains(element)){            //calling the contains method ensures the efficiency for sorted cases.
                while (count < entries) {
                    if (compare.getData().compareTo(element) == 0) {
                        return count;
                    }
                    compare = compare.getNext();
                    count += 1;
                }
            }
        }
        return -1;
    }

    // Return true if the list is empty and false otherwise.
    public boolean isEmpty() {
        if(entries==0)
            return true;
        return false;
    }

    /* Same as indexOf(), except it will return the last index
     of element. If isSorted is true, uses the ordering
     of the list to increase the efficiency of the search */
    public int lastIndexOf(T element) {
        int count = 0;
        Node<T> compare = headNode;
        int match = -1;
        if (isSorted && contains(element)) {
            while (count < entries) {
                if (compare.getData().compareTo(element) == 0) {
                    match = count;
                    if (compare.getNext().getData() != element)
                        return match;
                }
                compare = compare.getNext();
                count += 1;
            }
        } else {
            while (count < entries) {
                if (element != null) {
                    if (compare.getData() == element)
                        match = count;
                }
                compare = compare.getNext();
                count += 1;
            }
        }
        return match;
    }

    /* Replaces the element at index with element and return the
       element that was previously at index. If index is
       out-of-bounds or element is null, do nothing with element
       and return null */
    public T set (int index, T element) {
        T temp;
        if (element!=null && index >=0 && index<entries) {
            int count = 0;
            Node<T> replacedNode = headNode;
            while (count < index) {
                replacedNode = replacedNode.getNext();
                count += 1;
            }
            temp=replacedNode.getData();
            replacedNode.setData(element);

            return temp;
        }
        return null;
    }

    // Returns the number of elements in the list
    public int size(){
        return entries;
    }


    /*
     * Sort the elements of the list. If order is true, sort the
     * list in increasing (alphabeticaly) order. If order is
     * false, sort the list in decreasing (reverse alphabetical)
     * order.
     * If isSorted is true, and the order is true, do NOT resort..
     */
    public void sort(boolean order) {
        Node<T> comp;   //node we are comparing
        Node<T> curr;   //Current Node we are at
        T temp;

        if (order) {      //alphabetical sorting
            if (!isSorted) {
                for (int sortThru = 0; sortThru < entries - 1; sortThru++) {    //amounts of times that we sorted entries
                    curr = headNode;
                    comp = curr.getNext();
                    for (int goingThru = 0; goingThru < entries - sortThru - 1; goingThru++) {    //comparing all entries through the list
                        if (curr.getData().compareTo(comp.getData()) > 0) {   //information in current node is greater than to one compared
                            temp = curr.getData();
                            curr.setData(comp.getData());
                            comp.setData(temp);
                        }
                        curr = comp;
                        comp = comp.getNext();
                    }
                }
                isSorted = true;
            }
        }

        else {      //reverse alphabetical sorting
            for (int sortThru=0; sortThru<entries-1; sortThru++) {    //amounts of times that we sorted entries
                curr=headNode;
                comp=curr.getNext();
                for (int goingThru=0; goingThru<entries-sortThru-1; goingThru++) {    //comparing all entries through the list
                    if (curr.getData().compareTo(comp.getData()) < 0) {   //information in current node is greater than to one compared
                        temp = curr.getData();
                        curr.setData(comp.getData());
                        comp.setData(temp);
                    }
                    curr =comp;
                    comp=comp.getNext();
                }
            }
            isSorted=false;
        }


    }

    /*
     * Remove the first instance of element from the list. This
     * method should also shifts any subsequent elements to the
     * left (subtracts one from their indices). If successful,
     * return true. If element is not found in the list, return
     * false.
     */
    public boolean remove(T element) {
        if (contains(element)) {
            int index = indexOf(element);
            int count = 0;
            Node nodeBefore=null;
            Node parseNode = headNode;
            Node nodeAfter=headNode.getNext();
            while (count < index) {
                nodeBefore=parseNode;
                parseNode=nodeAfter;
                nodeAfter=parseNode.getNext();
                count += 1;
            }
            nodeBefore.setNext(nodeAfter);
            entries-=1;
            return true;
        }

        return false;
    }

    /*
     * Remove whatever is at index index in the list and return
     * it. If index is out-of-bounds, return null. Shift the
     * indices as in the other remove.
     */
    public T remove(int index) {
        if (index>0 && index < entries) {
            int count = 0;
            Node<T> nodeBefore = null;
            Node<T> parseNode = headNode;
            Node<T> nodeAfter = headNode.getNext();
            while (count < index) {
                nodeBefore = parseNode;
                parseNode = nodeAfter;
                nodeAfter = parseNode.getNext();
                count += 1;
            }
            nodeBefore.setNext(nodeAfter);
            entries -= 1;
            return parseNode.getData();
        }
        return null;
    }
}