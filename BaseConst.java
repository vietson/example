package com.effect.tdb.bs.common;

/**
 * Created by datha on 7/4/2018.
 */
public class BaseConst {

    /*Not found exception*/
    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "%s không tồn tại";

    /*Duplicate exception*/
    public static final String DUPLICATE_EXCEPTION_MESSAGE = "%s đã tồn tại";

    /*
    * System error message
    * */
    public static final String SYSTEM_ERROR_MESSAGE = "Đã xảy ra lỗi hệ thống : ";

    public static final String LANGUAGE_BASE_URL = "/language";
    public static final String LANGUAGE_FETCH_BY_LOCALE = "/fetchbylocale/{locale}";
    public static final String UPLOAD_URL = "/upload";
    public static final String GET_PAGE_URL = "/getpage";
    public static final String GET_ONE_CUS_URL = "/getonecus/{id}";
    public static final String GET_ONE_URL = "/getone/{id}";
    public static final String GET_ALL_URL = "/getall";
    public static final String GET_DETAIL_URL = "/getdetail/{id}";
    public static final String CREATE_URL = "/create";
    public static final String UPDATE_URL = "/update";

    public static final String DELETE_LOGICAL_URL = "/deleteLogical";
    public static final String TOKEN_VERIFY_URL = "/verify";

    public static final String STATUS_COLUMN = "status";
    public static final String AUTH_BASE_URL = "/auth";
    public static final String DELETE_URL = "/delete";

    public static final String TEMPLATE_LAYOUT = "/LayoutTemplate";
    public static final String BASE_LIST = "/baselist";

    public static final String TEMPLATE_GET_LAYOUT_GRID = "/getLayoutGrid";
    public static final String BASE_LIST_GET_LIST_DYNAMIC_BY_TABLE = "/getListPagingDynamicByTableName";
    public static final String BASE_LIST_GET_LIST_DATA_BY_TABLE = "/getDataByTableName";
    public static final String TEMPATE_UPDATE_LAYOUT_GRID = "/updateLayoutGrid";

    public static final String TEMPLATE_GET_LAYOUT_DETAIL = "/getLayoutDetail/{layoutTag}";

    public static final String TEMPATE_UPDATE_LAYOUT_DETAIL = "/updateLayoutDetail";

    public static final String DATE_TIME_FORMART_DB = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATE_TIME_FORMART_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMART_CLIENT = "yyyy-MM-dd";

}
