package schema;

import java.util.Objects;

/**
 * Representation of a JsonSchema reference; i.e. the {@code $ref} keyword; {@see
 * https://json-schema.org/latest/json-schema-core.html#rfc.section.8.3}
 *
 * Example: {@code type.json#/definitions/TAG}
 */
public class JsonSchemaRef {
    private final String refValue;

    /**
     * ctor
     *
     * @param refValue value of the $ref property (always a URI)
     */
    public JsonSchemaRef(String refValue) {
        this.refValue = refValue;
    }

    /** @return the 'file' part of the ref value (before the #) */
    public String getFile() {
        return refValue.split("#")[0];
    }

    /** @return the 'path' part of the ref value (after the #) */
    public String[] getPath() {
        return refValue.split("#")[1].split("/");
    }

    /** @return the 'name' of the path */
    public String getName() {
        String[] parts = refValue.split("#")[1].split("/");
        return parts[parts.length - 1];
    }

    /** @return the raw value */
    public String getValue() {
        return refValue;
    }

    @Override
    public String toString() {
        return refValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonSchemaRef)) {
            return false;
        }
        JsonSchemaRef that = (JsonSchemaRef) o;
        return Objects.equals(refValue, that.refValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refValue);
    }
}
