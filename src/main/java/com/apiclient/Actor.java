package com.apiclient;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.StringWriter;
import java.io.Writer;

public class Actor implements Jsonable {
    public int id;
    public int age;
    public String name;

    enum keys implements JsonKey {
        ID("id"),
        AGE("age"),
        NAME("name");

        private final Object value;

        /**
         * Instantiates a JsonKey with the provided value.
         *
         * @param value represents a valid default for the key.
         */
        keys(final Object value) {this.value = value;}

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            /* Can represent a valid default, error value, or null adhoc for the JsonKey. See the javadocs for more
             * information about its intended use. */
            return this.value;
        }
    }

    //Constructors
    public Actor() {
    }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Actor(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final Exception e) {
            /* See java.io.StringWriter. */
        }
        return writable.toString();
    }

    @Override
    public void toJson(final Writer writable) {
        try {
            final JsonObject json = new JsonObject();
            json.put(keys.ID.getKey(), this.getID());
            json.put(keys.NAME.getKey(), this.getName());
            json.put(keys.AGE.getKey(), this.getAge());
            json.toJson(writable);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public String toString() {
        return "JsonSimpleExample [id=\" + this.id + \", name=" + this.name + ", age=" + this.age + "]";
    }
}
