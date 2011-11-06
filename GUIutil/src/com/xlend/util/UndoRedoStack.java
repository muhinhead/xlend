package com.xlend.util;

import com.xlend.util.UndoRedoOperation;
import java.util.ArrayList;

/**
 *
 * @author Nick Mukhin
 */
public class UndoRedoStack {

    private ArrayList<UndoRedoOperation> list;
    private int pointer;

    public UndoRedoStack() {
        list = new ArrayList<UndoRedoOperation>();
        pointer = 0;
    }

    public void push(UndoRedoOperation op) {
        list.add(pointer++, op);
        //remove all elements after just inserted
        while (list.get(list.size() - 1) != op) {
            list.remove(list.size() - 1);
        }
    }

    public UndoRedoOperation undo() throws Exception {
        UndoRedoOperation op = null;
        if (!list.isEmpty() && pointer > 0) {
            op = list.get(pointer - 1);
            boolean ok = op.undo();
            if (ok) {
                pointer--;
            }
        }
        return op;
    }

    public UndoRedoOperation redo() throws Exception {
        UndoRedoOperation op = null;
        if (!list.isEmpty() && pointer < list.size()) {
            op = list.get(pointer);
            boolean ok = op.redo();
            if (ok) {
                pointer++;
            }
        }
        return op;
    }

    public void clear() {
        list.clear();
        pointer = 0;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
