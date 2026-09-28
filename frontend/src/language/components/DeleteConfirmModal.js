import React from "react";
import "./DeleteConfirmModal.css";

function DeleteConfirmModal({ onConfirm, onCancel }) {
    return (
        <div className="delete-modal__overlay" onClick={onCancel}>
            <div className="delete-modal" onClick={(e) => e.stopPropagation()}>
                <p className="delete-modal__text">Are you sure you want to delete?</p>
                <div className="delete-modal__buttons">
                    <button className="delete-modal__btn delete-modal__btn--yes" onClick={onConfirm}>Yes</button>
                    <button className="delete-modal__btn delete-modal__btn--no" onClick={onCancel}>No</button>
                </div>
            </div>
        </div>
    );
}

export default DeleteConfirmModal;