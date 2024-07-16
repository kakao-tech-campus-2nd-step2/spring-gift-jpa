package gift.main.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import gift.main.entity.Role;

import java.io.IOException;

public class ConverterRoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return Role.toRole(p.getValueAsString().toUpperCase());
    }
}
