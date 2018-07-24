package com.mitratestapp;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

    TextView t1_temp,t2_city,t3_description,t4_date;
    @Override
    protected String getMainComponentName() {
        return "MitraTestApp";

        t1_temp = (TextView)findViewById(R.id.textView);
        t2_city = (TextView)findViewById(R.id.textView3);
        t3_description = (TextView)findViewById(R.id.textView4);
        t4_date = (TextView)findViewById(R.id.textView2);

        find_weather();
    }

    public void find_weather() 
    {
        String url = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22&units=Imperial";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try 
                {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONObject array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    Calender calender = Calender.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted_date = sdf.format(calender.getTime());

                    t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));
                }
                catch(JSONException e)
                {
                    e.printStacktrace();
                }
                
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor); 
    }
}
