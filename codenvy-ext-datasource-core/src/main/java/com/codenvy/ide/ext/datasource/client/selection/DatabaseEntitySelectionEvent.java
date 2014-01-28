package com.codenvy.ide.ext.datasource.client.selection;

import com.codenvy.ide.ext.datasource.shared.DatabaseMetadataEntityDTO;
import com.google.gwt.event.shared.GwtEvent;

public class DatabaseEntitySelectionEvent extends GwtEvent<DatabaseEntitySelectionHandler> {

    /** Handler type. */
    private static Type<DatabaseEntitySelectionHandler> TYPE;

    /** The (new) selected item. */
    private final DatabaseMetadataEntityDTO             selection;

    public DatabaseEntitySelectionEvent(final DatabaseMetadataEntityDTO newSelection) {
        super();
        this.selection = newSelection;
    }

    @Override
    public GwtEvent.Type<DatabaseEntitySelectionHandler> getAssociatedType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<DatabaseEntitySelectionHandler>();
        }
        return TYPE;
    }

    @Override
    protected void dispatch(DatabaseEntitySelectionHandler handler) {
        handler.onDatabaseEntitySelection(this);
    }

    /**
     * Returns the selected item from this event.
     * 
     * @return the selected item
     */
    public DatabaseMetadataEntityDTO getSelection() {
        return selection;
    }

    /**
     * Returns the type associated with this event.
     * 
     * @return returns the handler type
     */
    public static Type<DatabaseEntitySelectionHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<DatabaseEntitySelectionHandler>();
        }
        return TYPE;
    }
}
