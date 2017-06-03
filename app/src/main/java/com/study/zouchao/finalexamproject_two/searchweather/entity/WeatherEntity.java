package com.study.zouchao.finalexamproject_two.searchweather.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class WeatherEntity {
    private List<HeWeatherBean> HeWeather;

    public List<HeWeatherBean> getHeWeather() {
        return HeWeather;
    }

    public void setHeWeather(List<HeWeatherBean> HeWeather) {
        this.HeWeather = HeWeather;
    }

    public static class HeWeatherBean {
        /**
         * aqi : {}
         * basic : {}
         * daily_forecast : []
         * hourly_forecast : []
         * now : {"cond":{"code":"300","txt":"阵雨"},"fl":"27","hum":"99","pcpn":"0.2","pres":"1005","tmp":"22","vis":"10","wind":{"deg":"79","dir":"东风","sc":"3-4","spd":"13"}}
         * status : ok
         * suggestion : {"comf":{"brf":"较舒适","txt":"白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},"flu":{"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"较不宜","txt":"有较强降水，建议您选择在室内进行健身休闲运动。"},"trav":{"brf":"一般","txt":"温度适宜，但风稍大，且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
         */

        private AqiBean aqi;
        private BasicBean basic;
        private NowBean now;
        private String status;
        private SuggestionBean suggestion;
        private List<?> daily_forecast;
        private List<?> hourly_forecast;

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }

        public List<?> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<?> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<?> getHourly_forecast() {
            return hourly_forecast;
        }

        public void setHourly_forecast(List<?> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }

        public static class AqiBean {
        }

        public static class BasicBean {
        }

        public static class NowBean {
            /**
             * cond : {"code":"300","txt":"阵雨"}
             * fl : 27
             * hum : 99
             * pcpn : 0.2
             * pres : 1005
             * tmp : 22
             * vis : 10
             * wind : {"deg":"79","dir":"东风","sc":"3-4","spd":"13"}
             */

            private CondBean cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private WindBean wind;

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class CondBean {
                /**
                 * code : 300
                 * txt : 阵雨
                 */

                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class WindBean {
                /**
                 * deg : 79
                 * dir : 东风
                 * sc : 3-4
                 * spd : 13
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }

            @Override
            public String toString() {
                return "NowBean{" +
                        "cond=" + cond +
                        ", fl='" + fl + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pcpn='" + pcpn + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp='" + tmp + '\'' +
                        ", vis='" + vis + '\'' +
                        ", wind=" + wind +
                        '}';
            }
        }

        public static class SuggestionBean {
            /**
             * comf : {"brf":"较舒适","txt":"白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。"}
             * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
             * drsg : {"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"}
             * flu : {"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"}
             * sport : {"brf":"较不宜","txt":"有较强降水，建议您选择在室内进行健身休闲运动。"}
             * trav : {"brf":"一般","txt":"温度适宜，但风稍大，且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。"}
             * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
             */

            private ComfBean comf;
            private CwBean cw;
            private DrsgBean drsg;
            private FluBean flu;
            private SportBean sport;
            private TravBean trav;
            private UvBean uv;

            public ComfBean getComf() {
                return comf;
            }

            public void setComf(ComfBean comf) {
                this.comf = comf;
            }

            public CwBean getCw() {
                return cw;
            }

            public void setCw(CwBean cw) {
                this.cw = cw;
            }

            public DrsgBean getDrsg() {
                return drsg;
            }

            public void setDrsg(DrsgBean drsg) {
                this.drsg = drsg;
            }

            public FluBean getFlu() {
                return flu;
            }

            public void setFlu(FluBean flu) {
                this.flu = flu;
            }

            public SportBean getSport() {
                return sport;
            }

            public void setSport(SportBean sport) {
                this.sport = sport;
            }

            public TravBean getTrav() {
                return trav;
            }

            public void setTrav(TravBean trav) {
                this.trav = trav;
            }

            public UvBean getUv() {
                return uv;
            }

            public void setUv(UvBean uv) {
                this.uv = uv;
            }

            @Override
            public String toString() {
                return "SuggestionBean{" +
                        "comf=" + comf +
                        ", cw=" + cw +
                        ", drsg=" + drsg +
                        ", flu=" + flu +
                        ", sport=" + sport +
                        ", trav=" + trav +
                        ", uv=" + uv +
                        '}';
            }

            public static class ComfBean {
                /**
                 * brf : 较舒适
                 * txt : 白天有雨，从而使空气湿度加大，会使人们感觉有点儿闷热，但早晚的天气很凉爽、舒适。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "ComfBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class CwBean {
                /**
                 * brf : 不宜
                 * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "CwBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class DrsgBean {
                /**
                 * brf : 舒适
                 * txt : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "DrsgBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class FluBean {
                /**
                 * brf : 较易发
                 * txt : 天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "FluBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class SportBean {
                /**
                 * brf : 较不宜
                 * txt : 有较强降水，建议您选择在室内进行健身休闲运动。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "SportBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class TravBean {
                /**
                 * brf : 一般
                 * txt : 温度适宜，但风稍大，且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "TravBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class UvBean {
                /**
                 * brf : 最弱
                 * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public String toString() {
                    return "UvBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }
        }
    }
}
