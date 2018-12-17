package cn.evake.auth.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysBaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysBaseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSysNameIsNull() {
            addCriterion("sys_name is null");
            return (Criteria) this;
        }

        public Criteria andSysNameIsNotNull() {
            addCriterion("sys_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysNameEqualTo(String value) {
            addCriterion("sys_name =", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameNotEqualTo(String value) {
            addCriterion("sys_name <>", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameGreaterThan(String value) {
            addCriterion("sys_name >", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_name >=", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameLessThan(String value) {
            addCriterion("sys_name <", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameLessThanOrEqualTo(String value) {
            addCriterion("sys_name <=", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameLike(String value) {
            addCriterion("sys_name like", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameNotLike(String value) {
            addCriterion("sys_name not like", value, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameIn(List<String> values) {
            addCriterion("sys_name in", values, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameNotIn(List<String> values) {
            addCriterion("sys_name not in", values, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameBetween(String value1, String value2) {
            addCriterion("sys_name between", value1, value2, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysNameNotBetween(String value1, String value2) {
            addCriterion("sys_name not between", value1, value2, "sysName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameIsNull() {
            addCriterion("sys_sort_name is null");
            return (Criteria) this;
        }

        public Criteria andSysSortNameIsNotNull() {
            addCriterion("sys_sort_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysSortNameEqualTo(String value) {
            addCriterion("sys_sort_name =", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameNotEqualTo(String value) {
            addCriterion("sys_sort_name <>", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameGreaterThan(String value) {
            addCriterion("sys_sort_name >", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_sort_name >=", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameLessThan(String value) {
            addCriterion("sys_sort_name <", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameLessThanOrEqualTo(String value) {
            addCriterion("sys_sort_name <=", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameLike(String value) {
            addCriterion("sys_sort_name like", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameNotLike(String value) {
            addCriterion("sys_sort_name not like", value, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameIn(List<String> values) {
            addCriterion("sys_sort_name in", values, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameNotIn(List<String> values) {
            addCriterion("sys_sort_name not in", values, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameBetween(String value1, String value2) {
            addCriterion("sys_sort_name between", value1, value2, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andSysSortNameNotBetween(String value1, String value2) {
            addCriterion("sys_sort_name not between", value1, value2, "sysSortName");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andSysLogIsNull() {
            addCriterion("sys_log is null");
            return (Criteria) this;
        }

        public Criteria andSysLogIsNotNull() {
            addCriterion("sys_log is not null");
            return (Criteria) this;
        }

        public Criteria andSysLogEqualTo(String value) {
            addCriterion("sys_log =", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogNotEqualTo(String value) {
            addCriterion("sys_log <>", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogGreaterThan(String value) {
            addCriterion("sys_log >", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogGreaterThanOrEqualTo(String value) {
            addCriterion("sys_log >=", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogLessThan(String value) {
            addCriterion("sys_log <", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogLessThanOrEqualTo(String value) {
            addCriterion("sys_log <=", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogLike(String value) {
            addCriterion("sys_log like", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogNotLike(String value) {
            addCriterion("sys_log not like", value, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogIn(List<String> values) {
            addCriterion("sys_log in", values, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogNotIn(List<String> values) {
            addCriterion("sys_log not in", values, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogBetween(String value1, String value2) {
            addCriterion("sys_log between", value1, value2, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysLogNotBetween(String value1, String value2) {
            addCriterion("sys_log not between", value1, value2, "sysLog");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameIsNull() {
            addCriterion("sys_manager_name is null");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameIsNotNull() {
            addCriterion("sys_manager_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameEqualTo(String value) {
            addCriterion("sys_manager_name =", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameNotEqualTo(String value) {
            addCriterion("sys_manager_name <>", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameGreaterThan(String value) {
            addCriterion("sys_manager_name >", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_manager_name >=", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameLessThan(String value) {
            addCriterion("sys_manager_name <", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameLessThanOrEqualTo(String value) {
            addCriterion("sys_manager_name <=", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameLike(String value) {
            addCriterion("sys_manager_name like", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameNotLike(String value) {
            addCriterion("sys_manager_name not like", value, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameIn(List<String> values) {
            addCriterion("sys_manager_name in", values, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameNotIn(List<String> values) {
            addCriterion("sys_manager_name not in", values, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameBetween(String value1, String value2) {
            addCriterion("sys_manager_name between", value1, value2, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerNameNotBetween(String value1, String value2) {
            addCriterion("sys_manager_name not between", value1, value2, "sysManagerName");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneIsNull() {
            addCriterion("sys_manager_phone is null");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneIsNotNull() {
            addCriterion("sys_manager_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneEqualTo(String value) {
            addCriterion("sys_manager_phone =", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneNotEqualTo(String value) {
            addCriterion("sys_manager_phone <>", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneGreaterThan(String value) {
            addCriterion("sys_manager_phone >", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("sys_manager_phone >=", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneLessThan(String value) {
            addCriterion("sys_manager_phone <", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneLessThanOrEqualTo(String value) {
            addCriterion("sys_manager_phone <=", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneLike(String value) {
            addCriterion("sys_manager_phone like", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneNotLike(String value) {
            addCriterion("sys_manager_phone not like", value, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneIn(List<String> values) {
            addCriterion("sys_manager_phone in", values, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneNotIn(List<String> values) {
            addCriterion("sys_manager_phone not in", values, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneBetween(String value1, String value2) {
            addCriterion("sys_manager_phone between", value1, value2, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerPhoneNotBetween(String value1, String value2) {
            addCriterion("sys_manager_phone not between", value1, value2, "sysManagerPhone");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailIsNull() {
            addCriterion("sys_manager_email is null");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailIsNotNull() {
            addCriterion("sys_manager_email is not null");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailEqualTo(String value) {
            addCriterion("sys_manager_email =", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailNotEqualTo(String value) {
            addCriterion("sys_manager_email <>", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailGreaterThan(String value) {
            addCriterion("sys_manager_email >", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailGreaterThanOrEqualTo(String value) {
            addCriterion("sys_manager_email >=", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailLessThan(String value) {
            addCriterion("sys_manager_email <", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailLessThanOrEqualTo(String value) {
            addCriterion("sys_manager_email <=", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailLike(String value) {
            addCriterion("sys_manager_email like", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailNotLike(String value) {
            addCriterion("sys_manager_email not like", value, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailIn(List<String> values) {
            addCriterion("sys_manager_email in", values, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailNotIn(List<String> values) {
            addCriterion("sys_manager_email not in", values, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailBetween(String value1, String value2) {
            addCriterion("sys_manager_email between", value1, value2, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andSysManagerEmailNotBetween(String value1, String value2) {
            addCriterion("sys_manager_email not between", value1, value2, "sysManagerEmail");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressIsNull() {
            addCriterion("online_express is null");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressIsNotNull() {
            addCriterion("online_express is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressEqualTo(String value) {
            addCriterion("online_express =", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressNotEqualTo(String value) {
            addCriterion("online_express <>", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressGreaterThan(String value) {
            addCriterion("online_express >", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressGreaterThanOrEqualTo(String value) {
            addCriterion("online_express >=", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressLessThan(String value) {
            addCriterion("online_express <", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressLessThanOrEqualTo(String value) {
            addCriterion("online_express <=", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressLike(String value) {
            addCriterion("online_express like", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressNotLike(String value) {
            addCriterion("online_express not like", value, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressIn(List<String> values) {
            addCriterion("online_express in", values, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressNotIn(List<String> values) {
            addCriterion("online_express not in", values, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressBetween(String value1, String value2) {
            addCriterion("online_express between", value1, value2, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andOnlineExpressNotBetween(String value1, String value2) {
            addCriterion("online_express not between", value1, value2, "onlineExpress");
            return (Criteria) this;
        }

        public Criteria andSingleLoginIsNull() {
            addCriterion("single_login is null");
            return (Criteria) this;
        }

        public Criteria andSingleLoginIsNotNull() {
            addCriterion("single_login is not null");
            return (Criteria) this;
        }

        public Criteria andSingleLoginEqualTo(Boolean value) {
            addCriterion("single_login =", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginNotEqualTo(Boolean value) {
            addCriterion("single_login <>", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginGreaterThan(Boolean value) {
            addCriterion("single_login >", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginGreaterThanOrEqualTo(Boolean value) {
            addCriterion("single_login >=", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginLessThan(Boolean value) {
            addCriterion("single_login <", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginLessThanOrEqualTo(Boolean value) {
            addCriterion("single_login <=", value, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginIn(List<Boolean> values) {
            addCriterion("single_login in", values, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginNotIn(List<Boolean> values) {
            addCriterion("single_login not in", values, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginBetween(Boolean value1, Boolean value2) {
            addCriterion("single_login between", value1, value2, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andSingleLoginNotBetween(Boolean value1, Boolean value2) {
            addCriterion("single_login not between", value1, value2, "singleLogin");
            return (Criteria) this;
        }

        public Criteria andPop3TitleIsNull() {
            addCriterion("pop3_title is null");
            return (Criteria) this;
        }

        public Criteria andPop3TitleIsNotNull() {
            addCriterion("pop3_title is not null");
            return (Criteria) this;
        }

        public Criteria andPop3TitleEqualTo(String value) {
            addCriterion("pop3_title =", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleNotEqualTo(String value) {
            addCriterion("pop3_title <>", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleGreaterThan(String value) {
            addCriterion("pop3_title >", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleGreaterThanOrEqualTo(String value) {
            addCriterion("pop3_title >=", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleLessThan(String value) {
            addCriterion("pop3_title <", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleLessThanOrEqualTo(String value) {
            addCriterion("pop3_title <=", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleLike(String value) {
            addCriterion("pop3_title like", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleNotLike(String value) {
            addCriterion("pop3_title not like", value, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleIn(List<String> values) {
            addCriterion("pop3_title in", values, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleNotIn(List<String> values) {
            addCriterion("pop3_title not in", values, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleBetween(String value1, String value2) {
            addCriterion("pop3_title between", value1, value2, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andPop3TitleNotBetween(String value1, String value2) {
            addCriterion("pop3_title not between", value1, value2, "pop3Title");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIsNull() {
            addCriterion("smtp_server is null");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIsNotNull() {
            addCriterion("smtp_server is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpServerEqualTo(String value) {
            addCriterion("smtp_server =", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotEqualTo(String value) {
            addCriterion("smtp_server <>", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerGreaterThan(String value) {
            addCriterion("smtp_server >", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_server >=", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLessThan(String value) {
            addCriterion("smtp_server <", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLessThanOrEqualTo(String value) {
            addCriterion("smtp_server <=", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLike(String value) {
            addCriterion("smtp_server like", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotLike(String value) {
            addCriterion("smtp_server not like", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIn(List<String> values) {
            addCriterion("smtp_server in", values, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotIn(List<String> values) {
            addCriterion("smtp_server not in", values, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerBetween(String value1, String value2) {
            addCriterion("smtp_server between", value1, value2, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotBetween(String value1, String value2) {
            addCriterion("smtp_server not between", value1, value2, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andPop3IsNull() {
            addCriterion("pop3 is null");
            return (Criteria) this;
        }

        public Criteria andPop3IsNotNull() {
            addCriterion("pop3 is not null");
            return (Criteria) this;
        }

        public Criteria andPop3EqualTo(String value) {
            addCriterion("pop3 =", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3NotEqualTo(String value) {
            addCriterion("pop3 <>", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3GreaterThan(String value) {
            addCriterion("pop3 >", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3GreaterThanOrEqualTo(String value) {
            addCriterion("pop3 >=", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3LessThan(String value) {
            addCriterion("pop3 <", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3LessThanOrEqualTo(String value) {
            addCriterion("pop3 <=", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3Like(String value) {
            addCriterion("pop3 like", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3NotLike(String value) {
            addCriterion("pop3 not like", value, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3In(List<String> values) {
            addCriterion("pop3 in", values, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3NotIn(List<String> values) {
            addCriterion("pop3 not in", values, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3Between(String value1, String value2) {
            addCriterion("pop3 between", value1, value2, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3NotBetween(String value1, String value2) {
            addCriterion("pop3 not between", value1, value2, "pop3");
            return (Criteria) this;
        }

        public Criteria andPop3mIsNull() {
            addCriterion("pop3m is null");
            return (Criteria) this;
        }

        public Criteria andPop3mIsNotNull() {
            addCriterion("pop3m is not null");
            return (Criteria) this;
        }

        public Criteria andPop3mEqualTo(String value) {
            addCriterion("pop3m =", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mNotEqualTo(String value) {
            addCriterion("pop3m <>", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mGreaterThan(String value) {
            addCriterion("pop3m >", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mGreaterThanOrEqualTo(String value) {
            addCriterion("pop3m >=", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mLessThan(String value) {
            addCriterion("pop3m <", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mLessThanOrEqualTo(String value) {
            addCriterion("pop3m <=", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mLike(String value) {
            addCriterion("pop3m like", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mNotLike(String value) {
            addCriterion("pop3m not like", value, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mIn(List<String> values) {
            addCriterion("pop3m in", values, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mNotIn(List<String> values) {
            addCriterion("pop3m not in", values, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mBetween(String value1, String value2) {
            addCriterion("pop3m between", value1, value2, "pop3m");
            return (Criteria) this;
        }

        public Criteria andPop3mNotBetween(String value1, String value2) {
            addCriterion("pop3m not between", value1, value2, "pop3m");
            return (Criteria) this;
        }

        public Criteria andLastUpUidIsNull() {
            addCriterion("last_up_uid is null");
            return (Criteria) this;
        }

        public Criteria andLastUpUidIsNotNull() {
            addCriterion("last_up_uid is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpUidEqualTo(String value) {
            addCriterion("last_up_uid =", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidNotEqualTo(String value) {
            addCriterion("last_up_uid <>", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidGreaterThan(String value) {
            addCriterion("last_up_uid >", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidGreaterThanOrEqualTo(String value) {
            addCriterion("last_up_uid >=", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidLessThan(String value) {
            addCriterion("last_up_uid <", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidLessThanOrEqualTo(String value) {
            addCriterion("last_up_uid <=", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidLike(String value) {
            addCriterion("last_up_uid like", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidNotLike(String value) {
            addCriterion("last_up_uid not like", value, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidIn(List<String> values) {
            addCriterion("last_up_uid in", values, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidNotIn(List<String> values) {
            addCriterion("last_up_uid not in", values, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidBetween(String value1, String value2) {
            addCriterion("last_up_uid between", value1, value2, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUpUidNotBetween(String value1, String value2) {
            addCriterion("last_up_uid not between", value1, value2, "lastUpUid");
            return (Criteria) this;
        }

        public Criteria andLastUnameIsNull() {
            addCriterion("last_uname is null");
            return (Criteria) this;
        }

        public Criteria andLastUnameIsNotNull() {
            addCriterion("last_uname is not null");
            return (Criteria) this;
        }

        public Criteria andLastUnameEqualTo(String value) {
            addCriterion("last_uname =", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameNotEqualTo(String value) {
            addCriterion("last_uname <>", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameGreaterThan(String value) {
            addCriterion("last_uname >", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameGreaterThanOrEqualTo(String value) {
            addCriterion("last_uname >=", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameLessThan(String value) {
            addCriterion("last_uname <", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameLessThanOrEqualTo(String value) {
            addCriterion("last_uname <=", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameLike(String value) {
            addCriterion("last_uname like", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameNotLike(String value) {
            addCriterion("last_uname not like", value, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameIn(List<String> values) {
            addCriterion("last_uname in", values, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameNotIn(List<String> values) {
            addCriterion("last_uname not in", values, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameBetween(String value1, String value2) {
            addCriterion("last_uname between", value1, value2, "lastUname");
            return (Criteria) this;
        }

        public Criteria andLastUnameNotBetween(String value1, String value2) {
            addCriterion("last_uname not between", value1, value2, "lastUname");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}