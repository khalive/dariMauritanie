package mr.example.darimaritanie.api;

import mr.example.darimaritanie.model.ChambreResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChambreApi {
    @GET("api/chambres")
    Call<ChambreResponse> getChambres();
}
