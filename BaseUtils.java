package com.effect.tdb.bs.common;

import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.effect.tdb.bs.common.BaseConst.DATE_TIME_FORMART_CLIENT;
import static com.effect.tdb.bs.common.BaseConst.DATE_TIME_FORMART_DB;


/**
 * Created by datha on 4/6/2018.
 */
public class BaseUtils {
    private static Logger logger = Logger.getLogger(BaseUtils.class);

    public static DataTablePaginationRequest getDatatableRequestInfo(MultiValueMap<String, String> params) {
        DataTablePaginationRequest dtpr = new DataTablePaginationRequest();
        try {
            int draw = Integer.parseInt(params.get("draw").get(0));
            int start = Integer.parseInt(params.get("start").get(0));
            int length = Integer.parseInt(params.get("length").get(0));
            String searchValue = params.get("search[value]").get(0);
            dtpr.setDraw(draw);
            dtpr.setStart(start);
            dtpr.setLength(length);
            dtpr.setSearchValue(searchValue);
        } catch (Exception e) {
            except(logger, e);
        }

        int i = 0;
        Map<Integer, DataTableColumn> columns = new HashMap<Integer, DataTableColumn>();
        while (params.containsKey(dataTableRequestGetColumnKey(i, "data"))) {
            int index = i;
            String data = params.get(dataTableRequestGetColumnKey(i, "data")).get(0);
            String name = params.get(dataTableRequestGetColumnKey(i, "name")).get(0);
            String searchValue = params.get(String.format("columns[%d][search][value]", i)).get(0);
            DataTableColumn dataTableColumn = new DataTableColumn(index, data, name, searchValue);
            columns.put(i, dataTableColumn);
            i++;
        }
        dtpr.setColumns(columns);

        List<FilterObject> filterCondition = new ArrayList<>();
        i = 0;
        while (params.containsKey(dataTableRequestGetFilterConditionKey(i, "fieldName"))) {
            int index = i;
            String fieldName = params.get(dataTableRequestGetFilterConditionKey(i, "fieldName")).get(0);
            String expression = params.get(dataTableRequestGetFilterConditionKey(i, "expression")).get(0);
            String value = params.get(dataTableRequestGetFilterConditionKey(i, "value")).get(0);
            String operator = params.get(dataTableRequestGetFilterConditionKey(i, "operator")).get(0);
            FilterObject filterObject = new FilterObject(fieldName, value, FilterType.getByValue(Integer.parseInt(expression)), FilterType.getByValue(Integer.parseInt(operator)));
            filterCondition.add(filterObject);
            i++;
        }
        dtpr.setFilter(filterCondition);

        String orderColumnIndex = params.get("order[0][column]").get(0);

        String orderDirection = params.get("order[0][dir]").get(0);

        dtpr.setOrderColumnIndex(Integer.parseInt(orderColumnIndex));
        dtpr.setOrderDirection(orderDirection);

        return dtpr;
    }

    public static String dataTableRequestGetColumnKey(int index, String propName) {
        return String.format("columns[%d][%s]", index, propName);
    }

    public static String dataTableRequestGetFilterConditionKey(int index, String propName) {
        return String.format("filterCondition[%d][%s]", index, propName);
    }

    public static void except(Logger _logger, Exception ex) {
        if (_logger == null) {
            _logger.info("logger is null, use BaseUtils logger for logging!");
        } else {
            _logger.error("Has exception. ", ex);
        }
    }

    public static Set<Character> arrayToSet(char... array) {
        Set<Character> toReturn;
        if (array == null) {
            return new HashSet<Character>();
        }
        toReturn = new HashSet<Character>(array.length);
        for (char c : array) {
            toReturn.add(c);
        }
        return toReturn;
    }

    public static MediaType getMTByFn(String fn) {
        if (fn == null) return null;
        try {
            String[] fnSplit = fn.split("\\.");

            if (fnSplit.length < 2) return MediaType.APPLICATION_OCTET_STREAM;

            String ext = fnSplit[fnSplit.length - 1];

            System.out.println("file ext: " + ext);

            if ("jpg".equalsIgnoreCase(ext)) {
                return MediaType.IMAGE_JPEG;
            }

            if ("png".equalsIgnoreCase(ext)) {
                return MediaType.IMAGE_PNG;
            }

            if ("gif".equalsIgnoreCase(ext)) {
                return MediaType.IMAGE_GIF;
            }

            return MediaType.APPLICATION_OCTET_STREAM;

        } catch (Exception e) {
            e.printStackTrace();
            return MediaType.APPLICATION_OCTET_STREAM;
        }

    }

    public static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }


    public static String getUploadFilePath(String uploadDir) {
        String uploadFullPath = System.getProperty("user.dir") + File.separator + uploadDir;
        return uploadFullPath;
    }

    /**
     * hàm Upload file từ chuỗi binary
     *
     * @param multipartFile file upload
     * @param id            id của đối tượng up ảnh
     * @param uploadDir     thư mục cần lưu trữ
     * @return trả về tên file + đuôi
     */
    public static String saveFile(MultipartFile multipartFile, Integer id, String uploadDir) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "";
        }
        String nameImage = "";
        try {
            String uploadFullPath = BaseUtils.getUploadFilePath(uploadDir);

            File file = new File(uploadFullPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            nameImage = id + "_" + System.currentTimeMillis() + "." + Files.getFileExtension(multipartFile.getOriginalFilename());

            String sImageUrl = uploadDir + File.separator + nameImage;

            System.out.println("file upload: " + sImageUrl);

            File fileSave = new File(sImageUrl);
            Files.write(multipartFile.getBytes(), fileSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameImage.replace("\\", "/");
    }

    /*
    * Convert kiểu data time từ database thành định dạng ở client
    * */
    public static String convertDateTimeFromDBToClient(String dateFromDB) {
        String dateToClient = null;
        if (!isEmpty(dateFromDB) && !isValidDateForClient(dateFromDB)) {
            LocalDateTime datetime = LocalDateTime.parse(dateFromDB, DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
            dateToClient = datetime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMART_CLIENT));
            return dateToClient;
        } else {
            return dateToClient;
        }
    }

    /*
    * Convert kiểu data time từ client thành định dạng ở database
    * */
    public static String convertDateTimeFromClientToDB(String dateFromClient) {
        String dateToDB = null;
        if (!isEmpty(dateFromClient) && isValidDateForClient(dateFromClient)) {
            dateFromClient += " 00:00:00.0";
            LocalDateTime datetime = LocalDateTime.parse(dateFromClient, DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
            dateToDB = datetime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
            return dateToDB;
        } else {
            return dateToDB;
        }
    }

    /*
    * Convert kiểu data time từ client thành định dạng ở database
    * */
    public static String convertDateTime(String dateFrom) {
        String dateTo = null;
        try {
            String dateFromForConvert = dateFrom + " 00:00:00.0";
            LocalDateTime datetime = LocalDateTime.parse(dateFromForConvert, DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
            dateTo = datetime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
        } catch (Exception e) {
            try {
                LocalDateTime datetime = LocalDateTime.parse(dateFrom, DateTimeFormatter.ofPattern(DATE_TIME_FORMART_DB));
                dateTo = datetime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMART_CLIENT));
            } catch (Exception ex) {
                e.printStackTrace();
                return null;
            }
        }
        return dateTo;
    }

    public static String convertDateTimeFromTimeStamp(Timestamp dateTimeFrom) {
        String dateTo = null;
        dateTo = new SimpleDateFormat(DATE_TIME_FORMART_CLIENT).format(dateTimeFrom);
        return dateTo;
    }

    public static Timestamp convertDateTimeFromString(String dateTimeFrom) {
        Timestamp dateTo = null;
        try {
            dateTo = (Timestamp) new SimpleDateFormat(DATE_TIME_FORMART_CLIENT).parse(dateTimeFrom);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return dateTo;
    }

    public static boolean isValidDateForDB(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMART_DB);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isValidDateForClient(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMART_CLIENT);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }

    public static boolean isEmpty(byte[] input) {
        return (input == null || input.length == 0);
    }

    public static boolean isEmpty(char[] input) {
        return (input == null || input.length == 0);
    }

    /**
     * generate ra câu where
     *
     * @param params MultiValueMap
     * @return trả lại chuỗi mệnh đề where
     */
    public static String generateWhereClause(@RequestParam MultiValueMap<String, String> params) {
        StringBuilder sWhere = new StringBuilder("");
        StringBuilder sbSearchValue = new StringBuilder("");
        StringBuilder sbSearchValueCustom = new StringBuilder("");
        int i = 0;
        String searchValueCustom = params.get("search[value]").get(0);
        while (params.containsKey(dataTableRequestGetColumnKey(i, "data"))) {
            int index = i;
            String data = params.get(dataTableRequestGetColumnKey(i, "data")).get(0);
            String searchValue = params.get(String.format("columns[%d][search][value]", i)).get(0);
            if (searchValue != null && !searchValue.isEmpty()) {
                if (sbSearchValue != null && !sbSearchValue.toString().equals("")) {
                    sbSearchValue.append(" AND " + data + " LIKE '%" + searchValue + "%'");
                } else {
                    sbSearchValue.append("  " + data + " LIKE '%" + searchValue + "%'");
                }
            }
            if (searchValueCustom != null && !searchValueCustom.isEmpty()) {
                if (sbSearchValueCustom != null && !sbSearchValueCustom.toString().equals("")) {
                    sbSearchValueCustom.append(" OR " + data + " LIKE '%" + searchValueCustom + "%'");
                } else {
                    sbSearchValueCustom.append(" " + data + " LIKE '%" + searchValueCustom + "%'");
                }
            }

            i++;
        }

        if (sbSearchValue != null && !sbSearchValue.toString().equals("")) {
            sbSearchValue.insert(0, "(").append(")");
            sWhere.append(sbSearchValue);
        }
        if (sbSearchValueCustom != null && !sbSearchValueCustom.toString().equals("")) {
            sbSearchValueCustom.insert(0, "(").append(")");
            if (sWhere != null && !sWhere.toString().equals("")) {
                sWhere.append(" AND ").append(sbSearchValueCustom);
            } else {
                sWhere.append(sbSearchValueCustom);
            }
        }


        if (sWhere != null && !sWhere.toString().equals("")) {
            sWhere.insert(0, " Where ");
        }
        return sWhere.toString();
    }

    /**
     * Buil câu sort
     */
    public static String generateSortOrder(@RequestParam MultiValueMap<String, String> params) {
        String sSort = "";
        String orderColumnIndex = params.get("order[0][column]").get(0);
        Integer positionColumn = Integer.parseInt(orderColumnIndex);
        String data = params.get(dataTableRequestGetColumnKey(positionColumn, "data")).get(0);
        String orderDirection = params.get("order[0][dir]").get(0);

        sSort = " " + data + " " + orderDirection;
        if (sSort != null && !orderDirection.trim().equals("")) {
            sSort = " order by " + sSort;
        }
        return sSort;
    }
}
