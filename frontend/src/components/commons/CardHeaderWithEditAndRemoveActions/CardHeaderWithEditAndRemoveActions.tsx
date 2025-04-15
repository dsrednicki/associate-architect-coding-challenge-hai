import React, { useEffect, useRef } from "react";
import "@ui5/webcomponents/dist/CardHeader.js";
import "@ui5/webcomponents/dist/Button.js";
import "@ui5/webcomponents/dist/Icon.js";

interface CardHeaderWithEditAndRemoveActionsProps {
    title: string;
    buttonTextForRemoveAction?: string;
    buttonTextForEditAction?:  string;
    onEditButtonClick?: () => void;
    onRemoveButtonClick?: () => void;
}

const CardHeaderWithEditAndRemoveActions: React.FC<CardHeaderWithEditAndRemoveActionsProps> = (
    { title, buttonTextForRemoveAction = "", buttonTextForEditAction = "", onEditButtonClick, onRemoveButtonClick }
) => {
    const removeButtonRef = useRef<HTMLElement>(null);
    const editButtonRef = useRef<HTMLElement>(null);

    useEffect(() => {
        const buttonElement = editButtonRef.current;
        if (buttonElement && onEditButtonClick) {
            const handleClick = () => onEditButtonClick();
            buttonElement.addEventListener("click", handleClick);

            // Cleanup the event listener on unmount
            return () => {
                buttonElement.removeEventListener("click", handleClick);
            };
        }
    }, [onEditButtonClick]);

    useEffect(() => {
        const buttonElement = removeButtonRef.current;
        if (buttonElement && onRemoveButtonClick) {
            const handleClick = () => onRemoveButtonClick();
            buttonElement.addEventListener("click", handleClick);

            // Cleanup the event listener on unmount
            return () => {
                buttonElement.removeEventListener("click", handleClick);
            };
        }
    }, [onRemoveButtonClick]);

    return (
        <ui5-card-header slot="header" title-text={title}>
            <ui5-icon name="clinical-task-tracker" slot="avatar"></ui5-icon>
            {onEditButtonClick && (
                <ui5-button
                    ref={editButtonRef}
                    icon="edit"
                    design="Transparent"
                    slot="action"
                    tooltip={buttonTextForEditAction}
                >
                    {buttonTextForEditAction}
                </ui5-button>
            )}
            {onRemoveButtonClick && (
            <ui5-button
                ref={removeButtonRef}
                icon="decline"
                design="Transparent"
                slot="action"
                tooltip={buttonTextForRemoveAction}
            >
                {buttonTextForRemoveAction}
            </ui5-button>
            )}
        </ui5-card-header>
    );
};

export default CardHeaderWithEditAndRemoveActions;
