package com.study.zouchao.finalexamproject_two.base_zou.api_zou;


import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;

import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 *
 *
 * 邹超专用！！！
 *
 *  Retrofit接口
 *
 *
 *
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("restapi/soa2/10129/GetTravelListForMobile.json?_fxpcqlniredt=09031155210068025364")
    Observable<TravelListResult> listTravelList(
            @FieldMap Map<String, String> params);
//
//    @GET("discuss/a/answer/list-username")
//    Observable<PersonInfoDetailAnswerResult> listPersonAnswerByGET(
//            @Query("username") String username);

//
}
