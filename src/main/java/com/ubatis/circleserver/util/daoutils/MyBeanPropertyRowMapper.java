package com.ubatis.circleserver.util.daoutils;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class MyBeanPropertyRowMapper<T> implements RowMapper<T> {


    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private Class<T> mappedClass;
    private boolean checkFullyPopulated = false;
    private boolean primitivesDefaultedForNullValue = false;
    @Nullable
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();
    @Nullable
    private Map<String, PropertyDescriptor> mappedFields;
    @Nullable
    private Set<String> mappedProperties;

    public MyBeanPropertyRowMapper() {
    }

    public MyBeanPropertyRowMapper(Class<T> mappedClass) {
        this.initialize(mappedClass);
    }

    public MyBeanPropertyRowMapper(Class<T> mappedClass, boolean checkFullyPopulated) {
        this.initialize(mappedClass);
        this.checkFullyPopulated = checkFullyPopulated;
    }

    public void setMappedClass(Class<T> mappedClass) {
        if (this.mappedClass == null) {
            this.initialize(mappedClass);
        } else if (this.mappedClass != mappedClass) {
            throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " + mappedClass + " since it is already providing mapping for " + this.mappedClass);
        }

    }

    @Nullable
    public final Class<T> getMappedClass() {
        return this.mappedClass;
    }

    public void setCheckFullyPopulated(boolean checkFullyPopulated) {
        this.checkFullyPopulated = checkFullyPopulated;
    }

    public boolean isCheckFullyPopulated() {
        return this.checkFullyPopulated;
    }

    public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
        this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
    }

    public boolean isPrimitivesDefaultedForNullValue() {
        return this.primitivesDefaultedForNullValue;
    }

    public void setConversionService(@Nullable ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nullable
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    protected void initialize(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap();
        this.mappedProperties = new HashSet();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        PropertyDescriptor[] var3 = pds;
        int var4 = pds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor pd = var3[var5];
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(this.lowerCaseName(pd.getName()), pd);
                String underscoredName = this.underscoreName(pd.getName());
                if (!this.lowerCaseName(pd.getName()).equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }

                this.mappedProperties.add(pd.getName());
            }
        }

    }

    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(this.lowerCaseName(name.substring(0, 1)));

            for(int i = 1; i < name.length(); ++i) {
                String s = name.substring(i, i + 1);
                String slc = this.lowerCaseName(s);
                if (!s.equals(slc)) {
                    result.append("_").append(slc);
                } else {
                    result.append(s);
                }
            }

            return result.toString();
        }
    }

    protected String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        this.initBeanWrapper(bw);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Set<String> populatedProperties = this.isCheckFullyPopulated() ? new HashSet() : null;

        for(int index = 1; index <= columnCount; ++index) {
            String column = JdbcUtils.lookupColumnName(rsmd, index);
            String field = this.lowerCaseName(StringUtils.delete(column, " "));
            PropertyDescriptor pd = this.mappedFields != null ? (PropertyDescriptor)this.mappedFields.get(field) : null;
            if (pd == null) {
                if (rowNumber == 0 && this.logger.isDebugEnabled()) {
                    this.logger.debug("No property found for column '" + column + "' mapped to field '" + field + "'");
                }
            } else {
                try {
                    Object value = this.getColumnValue(rs, index, pd);
                    if (rowNumber == 0 && this.logger.isDebugEnabled()) {
                        this.logger.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType()) + "'");
                    }

                    try {
                        bw.setPropertyValue(pd.getName(), value);
                    } catch (TypeMismatchException var14) {
                        if (value != null || !this.primitivesDefaultedForNullValue) {
                            throw var14;
                        }

                        if (this.logger.isDebugEnabled()) {
                            this.logger.debug("Intercepted TypeMismatchException for row " + rowNumber + " and column '" + column + "' with null value when setting property '" + pd.getName() + "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType()) + "' on object: " + mappedObject, var14);
                        }
                    }

                    if (populatedProperties != null) {
                        populatedProperties.add(pd.getName());
                    }
                } catch (NotWritablePropertyException var15) {
                    throw new DataRetrievalFailureException("Unable to map column '" + column + "' to property '" + pd.getName() + "'", var15);
                }
            }
        }

        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields necessary to populate object of class [" + this.mappedClass.getName() + "]: " + this.mappedProperties);
        } else {
            return mappedObject;
        }
    }

    protected void initBeanWrapper(BeanWrapper bw) {
        ConversionService cs = this.getConversionService();
        if (cs != null) {
            bw.setConversionService(cs);
        }

    }

    @Nullable
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
    }

    public static <T> BeanPropertyRowMapper<T> newInstance(Class<T> mappedClass) {
        return new BeanPropertyRowMapper(mappedClass);
    }

}
