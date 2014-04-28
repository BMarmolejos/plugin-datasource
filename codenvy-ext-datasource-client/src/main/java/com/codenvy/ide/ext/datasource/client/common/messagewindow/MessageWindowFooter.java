/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.ide.ext.datasource.client.common.messagewindow;

import javax.validation.constraints.NotNull;

import com.codenvy.ide.ext.datasource.client.common.confirmwindow.ConfirmWindowMessages;
import com.codenvy.ide.ext.datasource.client.common.messagewindow.MessageWindowView.ActionDelegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The footer show on message windows.
 * 
 * @author "Mickaël Leduque"
 */
public class MessageWindowFooter extends Composite {

    /** The UI binder instance. */
    private static MessageWindowFooterUiBinder uiBinder = GWT.create(MessageWindowFooterUiBinder.class);

    /** The action delegate. */
    private ActionDelegate                     actionDelegate;

    /** The i18n messages. */
    @UiField(provided = true)
    ConfirmWindowMessages                      messages;

    @Inject
    public MessageWindowFooter(final @NotNull ConfirmWindowMessages messages) {
        this.messages = messages;
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * Sets the action delegate.
     * 
     * @param delegate the new value
     */
    public void setDelegate(final ActionDelegate delegate) {
        this.actionDelegate = delegate;
    }

    /**
     * Handler set on the OK button.
     * 
     * @param event the event that triggers the handler call
     */
    @UiHandler("okButton")
    public void handleOkClick(final ClickEvent event) {
        this.actionDelegate.accepted();
    }

    /**
     * The UI binder interface for this component.
     * 
     * @author "Mickaël Leduque"
     */
    interface MessageWindowFooterUiBinder extends UiBinder<Widget, MessageWindowFooter> {
    }
}
