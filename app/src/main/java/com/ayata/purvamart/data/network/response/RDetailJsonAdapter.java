package com.ayata.purvamart.data.network.response;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Custom JsonAdapter for GSON to handle {@link RDetail} converstion
 *
 * @author Yavuz Tas
 *
 */
public class RDetailJsonAdapter extends TypeAdapter<RDetail> {

    @Override
    public void write(JsonWriter out, RDetail user) throws IOException {

        // Since we do not serialize RDetail by gson we can omit this part.
        // If you need you can check
        // com.google.gson.internal.bind.ObjectTypeAdapter class
        // read method for a basic object serialize implementation

    }

    @Override
    public RDetail read(JsonReader in) throws IOException {

        RDetail deserializedObject = new RDetail();

        // type of next token
        JsonToken peek = in.peek();

        // if the json field is string
        if (JsonToken.STRING.equals(peek)) {
            String stringValue = in.nextString();
            // convert string to integer and add to list as a value
            deserializedObject.add(stringValue);
        }

        // if it is array then implement normal array deserialization
        if (JsonToken.BEGIN_ARRAY.equals(peek)) {
            in.beginArray();

            while (in.hasNext()) {
                String element = in.nextString();
                deserializedObject.add(element);
            }

            in.endArray();
        }

        return deserializedObject;
    }
}