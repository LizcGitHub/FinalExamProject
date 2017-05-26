package com.study.zouchao.finalexamproject_two.travellist.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TravelListResult {
    /**
     * ResponseStatus : {"Timestamp":"/Date(1495727288273+0800)/","Ack":"Success","Errors":[],"Version":"1.0","Extension":[{"Id":"CLOGGING_TRACE_ID","Value":"5958761846536049117"}]}
     * Result : [{"Id":3300792,"TravelType":0,"IsOperationTravel":false,"Platform":2,"Title":"厦门：AMOY的身后，除了天空，皆是往事","CoverImageId":92277719,"PublishTime":"/Date(1481036112207-0000)/","UpdateTime":"/Date(1495608974814+0800)/","CommentCount":40,"VisitCount":69614,"LikeCount":549,"CollectionCount":80,"IsCollection":false,"IsLike":false,"DistrictId":21,"DistrictName":"厦门","DistrictEName":"Xiamen","CoverImageUrl":"https://youimg1.c-ctrip.com/target/100h0b0000005aaq4FD6D.jpg","DynamicPhotoUrl":"https://youimg1.c-ctrip.com/target/100h0b0000005aaq4FD6D.jpg","SmallCoverImageUrl":"https://dimg08.c-ctrip.com/images/100h0b0000005aaq4FD6D_R_150_150.jpg","Label":3,"Brief":"【旅行的意义】厦门的英文名AMOY，读起来很好听，却是起源于殖民时代，那一段哀伤的历史，却","UnReadRelplyCount":0,"CtripUid":"D01E39281EBFE44D96A6AAF229A96EC8","UserId":5289965,"Nickname":"贝小贤","UserPhoto":"https://images4.c-ctrip.com/target/Z80i0b00000058ior8EE6_C_180_180.jpg","Url":"http://you.ctrip.com/travels/xiamen21/3300792.html","Tags":[{"Id":3,"Name":"摄影","TagQouteId":3706106,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":17,"Name":"自由行","TagQouteId":3706105,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":26,"Name":"穷游","TagQouteId":3706110,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":21,"Name":"火车","TagQouteId":3706111,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":24,"Name":"小资","TagQouteId":3706108,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":1,"Name":"美食","TagQouteId":3706107,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":27,"Name":"省钱","TagQouteId":3706109,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1}],"Version":25,"DayCount":0,"PictureCount":174,"TravelStatus":1,"TravelDays":4,"Consume":2225,"CompanionType":3,"DepartureDate":"2016-02-10","TravellerLevel":1,"AuditorDate":"/Date(-62135596800000-0000)/","ClassicTravelImage":[],"CoverImageForList":0,"ImageCount":174,"SensitiveWords":[],"ShareCount":205,"SourceType":0,"DataLastTime":"/Date(1495609566492-0000)/","TravelDistricts":[],"TravelOperationStatus":101,"TravelPOI":[],"TravelSourceUserId":0,"TravelSeoTitle":"","PreAudit":1,"CoverLocationY":0,"ExpScore":0,"IsFirstPublish":false,"Score":398,"SystemAuditStatus":0,"ReplyId":0,"ReceivedUsefulInfoExp":false,"OprControlScore":0,"MobileSystemVersion":"","MemLevel":0,"LastReplyTime":"/Date(1495601568679-0000)/","IsRepublish":false,"IsReply":false,"IsOverSea":false,"H5Url":"https://m.ctrip.com/webapp/you/travels/Xiamen21/3300792.html","WebUrl":"http://you.ctrip.com/travels/xiamen21/3300792.html","IsOwner":false,"ContentView":"","IsBestTravel":false,"TravelContentFlag":[],"SubTitle":"AMOY，起源于殖民时代，一段哀伤的历史，却意外地给厦门带来了一个亲切的名字。"}]
     * Tokens : []
     * TotalCount : 15338
     * DistrictName : 厦门
     * NewTravelCount : 17
     */

    private ResponseStatusBean ResponseStatus;
    private int TotalCount;
    private String DistrictName;
    private int NewTravelCount;
    private List<ResultBean> Result;

    public ResponseStatusBean getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(ResponseStatusBean ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public int getNewTravelCount() {
        return NewTravelCount;
    }

    public void setNewTravelCount(int NewTravelCount) {
        this.NewTravelCount = NewTravelCount;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    @Override
    public String toString() {
        return "TravelListResult{" +
                "ResponseStatus=" + ResponseStatus +
                ", TotalCount=" + TotalCount +
                ", DistrictName='" + DistrictName + '\'' +
                ", NewTravelCount=" + NewTravelCount +
                ", Result=" + Result +
                '}';
    }

    public static class ResponseStatusBean {
        /**
         * Timestamp : /Date(1495727288273+0800)/
         * Ack : Success
         * Errors : []
         * Version : 1.0
         * Extension : [{"Id":"CLOGGING_TRACE_ID","Value":"5958761846536049117"}]
         */

        private String Timestamp;
        private String Ack;
        private String Version;
        private List<ExtensionBean> Extension;

        public String getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public List<ExtensionBean> getExtension() {
            return Extension;
        }

        public void setExtension(List<ExtensionBean> Extension) {
            this.Extension = Extension;
        }

        public static class ExtensionBean {
            /**
             * Id : CLOGGING_TRACE_ID
             * Value : 5958761846536049117
             */

            private String Id;
            private String Value;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
        }

        @Override
        public String toString() {
            return "ResponseStatusBean{" +
                    "Timestamp='" + Timestamp + '\'' +
                    ", Ack='" + Ack + '\'' +
                    ", Version='" + Version + '\'' +
                    ", Extension=" + Extension +
                    '}';
        }
    }

    public static class ResultBean {
        /**
         * Id : 3300792
         * TravelType : 0
         * IsOperationTravel : false
         * Platform : 2
         * Title : 厦门：AMOY的身后，除了天空，皆是往事
         * CoverImageId : 92277719
         * PublishTime : /Date(1481036112207-0000)/
         * UpdateTime : /Date(1495608974814+0800)/
         * CommentCount : 40
         * VisitCount : 69614
         * LikeCount : 549
         * CollectionCount : 80
         * IsCollection : false
         * IsLike : false
         * DistrictId : 21
         * DistrictName : 厦门
         * DistrictEName : Xiamen
         * CoverImageUrl : https://youimg1.c-ctrip.com/target/100h0b0000005aaq4FD6D.jpg
         * DynamicPhotoUrl : https://youimg1.c-ctrip.com/target/100h0b0000005aaq4FD6D.jpg
         * SmallCoverImageUrl : https://dimg08.c-ctrip.com/images/100h0b0000005aaq4FD6D_R_150_150.jpg
         * Label : 3
         * Brief : 【旅行的意义】厦门的英文名AMOY，读起来很好听，却是起源于殖民时代，那一段哀伤的历史，却
         * UnReadRelplyCount : 0
         * CtripUid : D01E39281EBFE44D96A6AAF229A96EC8
         * UserId : 5289965
         * Nickname : 贝小贤
         * UserPhoto : https://images4.c-ctrip.com/target/Z80i0b00000058ior8EE6_C_180_180.jpg
         * Url : http://you.ctrip.com/travels/xiamen21/3300792.html
         * Tags : [{"Id":3,"Name":"摄影","TagQouteId":3706106,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":17,"Name":"自由行","TagQouteId":3706105,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":26,"Name":"穷游","TagQouteId":3706110,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":21,"Name":"火车","TagQouteId":3706111,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":24,"Name":"小资","TagQouteId":3706108,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":1,"Name":"美食","TagQouteId":3706107,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1},{"Id":27,"Name":"省钱","TagQouteId":3706109,"TravelId":3300792,"IsCustomDefinition":false,"TravelTagStatus":1}]
         * Version : 25
         * DayCount : 0
         * PictureCount : 174
         * TravelStatus : 1
         * TravelDays : 4
         * Consume : 2225
         * CompanionType : 3
         * DepartureDate : 2016-02-10
         * TravellerLevel : 1
         * AuditorDate : /Date(-62135596800000-0000)/
         * ClassicTravelImage : []
         * CoverImageForList : 0
         * ImageCount : 174
         * SensitiveWords : []
         * ShareCount : 205
         * SourceType : 0
         * DataLastTime : /Date(1495609566492-0000)/
         * TravelDistricts : []
         * TravelOperationStatus : 101
         * TravelPOI : []
         * TravelSourceUserId : 0
         * TravelSeoTitle :
         * PreAudit : 1
         * CoverLocationY : 0
         * ExpScore : 0
         * IsFirstPublish : false
         * Score : 398
         * SystemAuditStatus : 0
         * ReplyId : 0
         * ReceivedUsefulInfoExp : false
         * OprControlScore : 0
         * MobileSystemVersion :
         * MemLevel : 0
         * LastReplyTime : /Date(1495601568679-0000)/
         * IsRepublish : false
         * IsReply : false
         * IsOverSea : false
         * H5Url : https://m.ctrip.com/webapp/you/travels/Xiamen21/3300792.html
         * WebUrl : http://you.ctrip.com/travels/xiamen21/3300792.html
         * IsOwner : false
         * ContentView :
         * IsBestTravel : false
         * TravelContentFlag : []
         * SubTitle : AMOY，起源于殖民时代，一段哀伤的历史，却意外地给厦门带来了一个亲切的名字。
         */

        private int Id;
        private int TravelType;
        private boolean IsOperationTravel;
        private int Platform;
        private String Title;
        private int CoverImageId;
        private String PublishTime;
        private String UpdateTime;
        private int CommentCount;
        private int VisitCount;
        private int LikeCount;
        private int CollectionCount;
        private boolean IsCollection;
        private boolean IsLike;
        private int DistrictId;
        private String DistrictName;
        private String DistrictEName;
        private String CoverImageUrl;
        private String DynamicPhotoUrl;
        private String SmallCoverImageUrl;
        private int Label;
        private String Brief;
        private int UnReadRelplyCount;
        private String CtripUid;
        private int UserId;
        private String Nickname;
        private String UserPhoto;
        private String Url;
        private int Version;
        private int DayCount;
        private int PictureCount;
        private int TravelStatus;
        private int TravelDays;
        private int Consume;
        private int CompanionType;
        private String DepartureDate;
        private int TravellerLevel;
        private String AuditorDate;
        private int CoverImageForList;
        private int ImageCount;
        private int ShareCount;
        private int SourceType;
        private String DataLastTime;
        private int TravelOperationStatus;
        private int TravelSourceUserId;
        private String TravelSeoTitle;
        private int PreAudit;
        private int CoverLocationY;
        private int ExpScore;
        private boolean IsFirstPublish;
        private int Score;
        private int SystemAuditStatus;
        private int ReplyId;
        private boolean ReceivedUsefulInfoExp;
        private int OprControlScore;
        private String MobileSystemVersion;
        private int MemLevel;
        private String LastReplyTime;
        private boolean IsRepublish;
        private boolean IsReply;
        private boolean IsOverSea;
        private String H5Url;
        private String WebUrl;
        private boolean IsOwner;
        private String ContentView;
        private boolean IsBestTravel;
        private String SubTitle;
        private List<TagsBean> Tags;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getTravelType() {
            return TravelType;
        }

        public void setTravelType(int TravelType) {
            this.TravelType = TravelType;
        }

        public boolean isIsOperationTravel() {
            return IsOperationTravel;
        }

        public void setIsOperationTravel(boolean IsOperationTravel) {
            this.IsOperationTravel = IsOperationTravel;
        }

        public int getPlatform() {
            return Platform;
        }

        public void setPlatform(int Platform) {
            this.Platform = Platform;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getCoverImageId() {
            return CoverImageId;
        }

        public void setCoverImageId(int CoverImageId) {
            this.CoverImageId = CoverImageId;
        }

        public String getPublishTime() {
            return PublishTime;
        }

        public void setPublishTime(String PublishTime) {
            this.PublishTime = PublishTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public int getVisitCount() {
            return VisitCount;
        }

        public void setVisitCount(int VisitCount) {
            this.VisitCount = VisitCount;
        }

        public int getLikeCount() {
            return LikeCount;
        }

        public void setLikeCount(int LikeCount) {
            this.LikeCount = LikeCount;
        }

        public int getCollectionCount() {
            return CollectionCount;
        }

        public void setCollectionCount(int CollectionCount) {
            this.CollectionCount = CollectionCount;
        }

        public boolean isIsCollection() {
            return IsCollection;
        }

        public void setIsCollection(boolean IsCollection) {
            this.IsCollection = IsCollection;
        }

        public boolean isIsLike() {
            return IsLike;
        }

        public void setIsLike(boolean IsLike) {
            this.IsLike = IsLike;
        }

        public int getDistrictId() {
            return DistrictId;
        }

        public void setDistrictId(int DistrictId) {
            this.DistrictId = DistrictId;
        }

        public String getDistrictName() {
            return DistrictName;
        }

        public void setDistrictName(String DistrictName) {
            this.DistrictName = DistrictName;
        }

        public String getDistrictEName() {
            return DistrictEName;
        }

        public void setDistrictEName(String DistrictEName) {
            this.DistrictEName = DistrictEName;
        }

        public String getCoverImageUrl() {
            return CoverImageUrl;
        }

        public void setCoverImageUrl(String CoverImageUrl) {
            this.CoverImageUrl = CoverImageUrl;
        }

        public String getDynamicPhotoUrl() {
            return DynamicPhotoUrl;
        }

        public void setDynamicPhotoUrl(String DynamicPhotoUrl) {
            this.DynamicPhotoUrl = DynamicPhotoUrl;
        }

        public String getSmallCoverImageUrl() {
            return SmallCoverImageUrl;
        }

        public void setSmallCoverImageUrl(String SmallCoverImageUrl) {
            this.SmallCoverImageUrl = SmallCoverImageUrl;
        }

        public int getLabel() {
            return Label;
        }

        public void setLabel(int Label) {
            this.Label = Label;
        }

        public String getBrief() {
            return Brief;
        }

        public void setBrief(String Brief) {
            this.Brief = Brief;
        }

        public int getUnReadRelplyCount() {
            return UnReadRelplyCount;
        }

        public void setUnReadRelplyCount(int UnReadRelplyCount) {
            this.UnReadRelplyCount = UnReadRelplyCount;
        }

        public String getCtripUid() {
            return CtripUid;
        }

        public void setCtripUid(String CtripUid) {
            this.CtripUid = CtripUid;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String Nickname) {
            this.Nickname = Nickname;
        }

        public String getUserPhoto() {
            return UserPhoto;
        }

        public void setUserPhoto(String UserPhoto) {
            this.UserPhoto = UserPhoto;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public int getVersion() {
            return Version;
        }

        public void setVersion(int Version) {
            this.Version = Version;
        }

        public int getDayCount() {
            return DayCount;
        }

        public void setDayCount(int DayCount) {
            this.DayCount = DayCount;
        }

        public int getPictureCount() {
            return PictureCount;
        }

        public void setPictureCount(int PictureCount) {
            this.PictureCount = PictureCount;
        }

        public int getTravelStatus() {
            return TravelStatus;
        }

        public void setTravelStatus(int TravelStatus) {
            this.TravelStatus = TravelStatus;
        }

        public int getTravelDays() {
            return TravelDays;
        }

        public void setTravelDays(int TravelDays) {
            this.TravelDays = TravelDays;
        }

        public int getConsume() {
            return Consume;
        }

        public void setConsume(int Consume) {
            this.Consume = Consume;
        }

        public int getCompanionType() {
            return CompanionType;
        }

        public void setCompanionType(int CompanionType) {
            this.CompanionType = CompanionType;
        }

        public String getDepartureDate() {
            return DepartureDate;
        }

        public void setDepartureDate(String DepartureDate) {
            this.DepartureDate = DepartureDate;
        }

        public int getTravellerLevel() {
            return TravellerLevel;
        }

        public void setTravellerLevel(int TravellerLevel) {
            this.TravellerLevel = TravellerLevel;
        }

        public String getAuditorDate() {
            return AuditorDate;
        }

        public void setAuditorDate(String AuditorDate) {
            this.AuditorDate = AuditorDate;
        }

        public int getCoverImageForList() {
            return CoverImageForList;
        }

        public void setCoverImageForList(int CoverImageForList) {
            this.CoverImageForList = CoverImageForList;
        }

        public int getImageCount() {
            return ImageCount;
        }

        public void setImageCount(int ImageCount) {
            this.ImageCount = ImageCount;
        }

        public int getShareCount() {
            return ShareCount;
        }

        public void setShareCount(int ShareCount) {
            this.ShareCount = ShareCount;
        }

        public int getSourceType() {
            return SourceType;
        }

        public void setSourceType(int SourceType) {
            this.SourceType = SourceType;
        }

        public String getDataLastTime() {
            return DataLastTime;
        }

        public void setDataLastTime(String DataLastTime) {
            this.DataLastTime = DataLastTime;
        }

        public int getTravelOperationStatus() {
            return TravelOperationStatus;
        }

        public void setTravelOperationStatus(int TravelOperationStatus) {
            this.TravelOperationStatus = TravelOperationStatus;
        }

        public int getTravelSourceUserId() {
            return TravelSourceUserId;
        }

        public void setTravelSourceUserId(int TravelSourceUserId) {
            this.TravelSourceUserId = TravelSourceUserId;
        }

        public String getTravelSeoTitle() {
            return TravelSeoTitle;
        }

        public void setTravelSeoTitle(String TravelSeoTitle) {
            this.TravelSeoTitle = TravelSeoTitle;
        }

        public int getPreAudit() {
            return PreAudit;
        }

        public void setPreAudit(int PreAudit) {
            this.PreAudit = PreAudit;
        }

        public int getCoverLocationY() {
            return CoverLocationY;
        }

        public void setCoverLocationY(int CoverLocationY) {
            this.CoverLocationY = CoverLocationY;
        }

        public int getExpScore() {
            return ExpScore;
        }

        public void setExpScore(int ExpScore) {
            this.ExpScore = ExpScore;
        }

        public boolean isIsFirstPublish() {
            return IsFirstPublish;
        }

        public void setIsFirstPublish(boolean IsFirstPublish) {
            this.IsFirstPublish = IsFirstPublish;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getSystemAuditStatus() {
            return SystemAuditStatus;
        }

        public void setSystemAuditStatus(int SystemAuditStatus) {
            this.SystemAuditStatus = SystemAuditStatus;
        }

        public int getReplyId() {
            return ReplyId;
        }

        public void setReplyId(int ReplyId) {
            this.ReplyId = ReplyId;
        }

        public boolean isReceivedUsefulInfoExp() {
            return ReceivedUsefulInfoExp;
        }

        public void setReceivedUsefulInfoExp(boolean ReceivedUsefulInfoExp) {
            this.ReceivedUsefulInfoExp = ReceivedUsefulInfoExp;
        }

        public int getOprControlScore() {
            return OprControlScore;
        }

        public void setOprControlScore(int OprControlScore) {
            this.OprControlScore = OprControlScore;
        }

        public String getMobileSystemVersion() {
            return MobileSystemVersion;
        }

        public void setMobileSystemVersion(String MobileSystemVersion) {
            this.MobileSystemVersion = MobileSystemVersion;
        }

        public int getMemLevel() {
            return MemLevel;
        }

        public void setMemLevel(int MemLevel) {
            this.MemLevel = MemLevel;
        }

        public String getLastReplyTime() {
            return LastReplyTime;
        }

        public void setLastReplyTime(String LastReplyTime) {
            this.LastReplyTime = LastReplyTime;
        }

        public boolean isIsRepublish() {
            return IsRepublish;
        }

        public void setIsRepublish(boolean IsRepublish) {
            this.IsRepublish = IsRepublish;
        }

        public boolean isIsReply() {
            return IsReply;
        }

        public void setIsReply(boolean IsReply) {
            this.IsReply = IsReply;
        }

        public boolean isIsOverSea() {
            return IsOverSea;
        }

        public void setIsOverSea(boolean IsOverSea) {
            this.IsOverSea = IsOverSea;
        }

        public String getH5Url() {
            return H5Url;
        }

        public void setH5Url(String H5Url) {
            this.H5Url = H5Url;
        }

        public String getWebUrl() {
            return WebUrl;
        }

        public void setWebUrl(String WebUrl) {
            this.WebUrl = WebUrl;
        }

        public boolean isIsOwner() {
            return IsOwner;
        }

        public void setIsOwner(boolean IsOwner) {
            this.IsOwner = IsOwner;
        }

        public String getContentView() {
            return ContentView;
        }

        public void setContentView(String ContentView) {
            this.ContentView = ContentView;
        }

        public boolean isIsBestTravel() {
            return IsBestTravel;
        }

        public void setIsBestTravel(boolean IsBestTravel) {
            this.IsBestTravel = IsBestTravel;
        }

        public String getSubTitle() {
            return SubTitle;
        }

        public void setSubTitle(String SubTitle) {
            this.SubTitle = SubTitle;
        }

        public List<TagsBean> getTags() {
            return Tags;
        }

        public void setTags(List<TagsBean> Tags) {
            this.Tags = Tags;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "Id=" + Id +
                    ", TravelType=" + TravelType +
                    ", IsOperationTravel=" + IsOperationTravel +
                    ", Platform=" + Platform +
                    ", Title='" + Title + '\'' +
                    ", CoverImageId=" + CoverImageId +
                    ", PublishTime='" + PublishTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", CommentCount=" + CommentCount +
                    ", VisitCount=" + VisitCount +
                    ", LikeCount=" + LikeCount +
                    ", CollectionCount=" + CollectionCount +
                    ", IsCollection=" + IsCollection +
                    ", IsLike=" + IsLike +
                    ", DistrictId=" + DistrictId +
                    ", DistrictName='" + DistrictName + '\'' +
                    ", DistrictEName='" + DistrictEName + '\'' +
                    ", CoverImageUrl='" + CoverImageUrl + '\'' +
                    ", DynamicPhotoUrl='" + DynamicPhotoUrl + '\'' +
                    ", SmallCoverImageUrl='" + SmallCoverImageUrl + '\'' +
                    ", Label=" + Label +
                    ", Brief='" + Brief + '\'' +
                    ", UnReadRelplyCount=" + UnReadRelplyCount +
                    ", CtripUid='" + CtripUid + '\'' +
                    ", UserId=" + UserId +
                    ", Nickname='" + Nickname + '\'' +
                    ", UserPhoto='" + UserPhoto + '\'' +
                    ", Url='" + Url + '\'' +
                    ", Version=" + Version +
                    ", DayCount=" + DayCount +
                    ", PictureCount=" + PictureCount +
                    ", TravelStatus=" + TravelStatus +
                    ", TravelDays=" + TravelDays +
                    ", Consume=" + Consume +
                    ", CompanionType=" + CompanionType +
                    ", DepartureDate='" + DepartureDate + '\'' +
                    ", TravellerLevel=" + TravellerLevel +
                    ", AuditorDate='" + AuditorDate + '\'' +
                    ", CoverImageForList=" + CoverImageForList +
                    ", ImageCount=" + ImageCount +
                    ", ShareCount=" + ShareCount +
                    ", SourceType=" + SourceType +
                    ", DataLastTime='" + DataLastTime + '\'' +
                    ", TravelOperationStatus=" + TravelOperationStatus +
                    ", TravelSourceUserId=" + TravelSourceUserId +
                    ", TravelSeoTitle='" + TravelSeoTitle + '\'' +
                    ", PreAudit=" + PreAudit +
                    ", CoverLocationY=" + CoverLocationY +
                    ", ExpScore=" + ExpScore +
                    ", IsFirstPublish=" + IsFirstPublish +
                    ", Score=" + Score +
                    ", SystemAuditStatus=" + SystemAuditStatus +
                    ", ReplyId=" + ReplyId +
                    ", ReceivedUsefulInfoExp=" + ReceivedUsefulInfoExp +
                    ", OprControlScore=" + OprControlScore +
                    ", MobileSystemVersion='" + MobileSystemVersion + '\'' +
                    ", MemLevel=" + MemLevel +
                    ", LastReplyTime='" + LastReplyTime + '\'' +
                    ", IsRepublish=" + IsRepublish +
                    ", IsReply=" + IsReply +
                    ", IsOverSea=" + IsOverSea +
                    ", H5Url='" + H5Url + '\'' +
                    ", WebUrl='" + WebUrl + '\'' +
                    ", IsOwner=" + IsOwner +
                    ", ContentView='" + ContentView + '\'' +
                    ", IsBestTravel=" + IsBestTravel +
                    ", SubTitle='" + SubTitle + '\'' +
                    ", Tags=" + Tags +
                    '}';
        }

        public static class TagsBean {
            /**
             * Id : 3
             * Name : 摄影
             * TagQouteId : 3706106
             * TravelId : 3300792
             * IsCustomDefinition : false
             * TravelTagStatus : 1
             */

            private int Id;
            private String Name;
            private int TagQouteId;
            private int TravelId;
            private boolean IsCustomDefinition;
            private int TravelTagStatus;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getTagQouteId() {
                return TagQouteId;
            }

            public void setTagQouteId(int TagQouteId) {
                this.TagQouteId = TagQouteId;
            }

            public int getTravelId() {
                return TravelId;
            }

            public void setTravelId(int TravelId) {
                this.TravelId = TravelId;
            }

            public boolean isIsCustomDefinition() {
                return IsCustomDefinition;
            }

            public void setIsCustomDefinition(boolean IsCustomDefinition) {
                this.IsCustomDefinition = IsCustomDefinition;
            }

            public int getTravelTagStatus() {
                return TravelTagStatus;
            }

            public void setTravelTagStatus(int TravelTagStatus) {
                this.TravelTagStatus = TravelTagStatus;
            }

            @Override
            public String toString() {
                return "TagsBean{" +
                        "Id=" + Id +
                        ", Name='" + Name + '\'' +
                        ", TagQouteId=" + TagQouteId +
                        ", TravelId=" + TravelId +
                        ", IsCustomDefinition=" + IsCustomDefinition +
                        ", TravelTagStatus=" + TravelTagStatus +
                        '}';
            }
        }
    }
}
