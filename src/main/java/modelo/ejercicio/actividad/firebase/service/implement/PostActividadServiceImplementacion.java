package modelo.ejercicio.actividad.firebase.service.implement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import modelo.ejercicio.actividad.firebase.dto.PostDTO;
import modelo.ejercicio.actividad.firebase.firebaze.FirebaseInitializer;
import modelo.ejercicio.actividad.firebase.service.PostActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostActividadServiceImplementacion implements PostActividadService {
    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<PostDTO> list() {
       return null;
    }

    @Override
    public Boolean add(PostDTO post) {
        Map<String, Object> docData = getDocData(post);

        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document().create(docData);

        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean edit(String id, PostDTO post) {
       return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("frecuencia");
    }

    private Map<String, Object> getDocData(PostDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("idUser", post.getIdUser());
        docData.put("fecha", post.getFecha());
        docData.put("hora", post.getHora());
        docData.put("content", post.getContent());
        return docData;
    }
}
