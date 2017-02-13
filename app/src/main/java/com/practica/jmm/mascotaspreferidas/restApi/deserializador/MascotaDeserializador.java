package com.practica.jmm.mascotaspreferidas.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.practica.jmm.mascotaspreferidas.pojo.Mascota;
import com.practica.jmm.mascotaspreferidas.restApi.JsonKeys;
import com.practica.jmm.mascotaspreferidas.restApi.model.PetResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by sath on 19/01/17.
 */

public class MascotaDeserializador implements JsonDeserializer<PetResponse> {
    @Override
    public PetResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        PetResponse petResponse = gson.fromJson(json,PetResponse.class);
        JsonArray jsonArray = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        petResponse.setMascotas(deserializarMascotaDeJson(jsonArray));
        return petResponse;
    }

    private ArrayList<Mascota> deserializarMascotaDeJson(JsonArray mascotaResponseData){

        ArrayList<Mascota> mascotas = new ArrayList<>();
        for (int i=0;i < mascotaResponseData.size(); i++){
            JsonObject mascotaResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();

            String id_media = mascotaResponseDataObject.get(JsonKeys.ID_MEDIA).getAsString();

            JsonObject userJson = mascotaResponseDataObject.getAsJsonObject(JsonKeys.USER);
            String id = userJson.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto = userJson.get(JsonKeys.USER_FULLNAME).getAsString();

            JsonObject urlFotoJson = mascotaResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_IMAGES);
            JsonObject standarResolutionJson = urlFotoJson.getAsJsonObject(JsonKeys.MEDIA_STANDARD_RESOLUTION);

            String urlFoto = standarResolutionJson.get(JsonKeys.MEDIA_URL).getAsString();

            JsonObject likesJson = mascotaResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_LIKES);
            //int numLikes = likesJson.get(JsonKeys.MEDIA_LIKES_COUNT).getAsInt();
            String numLikes = likesJson.get(JsonKeys.MEDIA_LIKES_COUNT).getAsString();

            Mascota mascotaActual = new Mascota();
            mascotaActual.setId(id);
            mascotaActual.setNombre(nombreCompleto);
            mascotaActual.setImagen(urlFoto);
            mascotaActual.setRaiting(numLikes);
            mascotaActual.setId_medeia(id_media);

            mascotas.add(mascotaActual);
        }
        return mascotas;
    }
}
