package util;

/**
 * Annotation to indicate that the class implements checked methods. That is,
 * methods that ensure the correctness of the structure and prevents most expected
 * logic mistakes (that end up in null pointers, index out of bounds, amongst others).
 */
public @interface Checked
{
    /**
     * Represents the class' notes for the 'Checked' tag. That is, the information
     * regarding the 'Checked' methods in the class' implementation (the meaning and
     * errors and exceptions the class is prone to).
     * @return The class' notes for the 'Checked' tag.
     */
    String note();
}
