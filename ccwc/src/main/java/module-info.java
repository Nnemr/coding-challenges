module ccwc.main {
    requires info.picocli;
    exports com.yanemr.ccwc;
    opens com.yanemr.ccwc to info.picocli;
}