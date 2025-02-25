package com.example.buddy_match.util;

import com.example.buddy_match.model.atest.Team;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@Data
public class CustomSpecs<T>  {
    /** This is an example. */
    private Stack<Operator> boolOps;

    /** This is an example. */
    private Stack<Object> valueList;

    /** This is an example. */
    private Stack<String> fieldList;

    /** This is an example. */
    private Stack<Operator> compOpList;

    public CustomSpecs() {
        this.boolOps = new Stack<Operator>();
        this.valueList = new Stack<Object>();
        this.fieldList = new Stack<String>();
        this.compOpList = new Stack<Operator>();
    }

    @Nullable
    public Specification<T> _toPredicate(String fieldKey, Object fieldValue, Operator compOp) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            switch (compOp) {
            case EQUAL:
                return cb.equal(root.get(fieldKey), fieldValue);
            case NOT_EQUAL:
                return cb.notEqual(root.get(fieldKey), fieldValue);
                case LIKE:
                    return cb.like(root.get(fieldKey), "%" + fieldValue + "%");
                case NOT_LIKE:
                    return cb.notLike(root.get(fieldKey), "%" + fieldValue + "%");
                case Is_Not_Null:
                    return cb.isNotNull(root.get(fieldKey));
                    case GREATER_THAN:
                        return cb.greaterThan(root.get(fieldKey), (Comparable) fieldValue);
                        case LESS_THAN:
                            return cb.lessThan(root.get(fieldKey), (Comparable) fieldValue);
                            case IN:
                                CriteriaBuilder.In<Object> in = cb.in(root.get(fieldKey));
                                if (fieldValue instanceof Collection) {
                                    for (Object value : (Collection<?>) fieldValue) {
                                        in.value(value);
                                    }
                                }
                                return in;
                                default:
                                    throw new UnsupportedOperationException("Unsupported operator: " + compOp);
                                }
                            };
    }

    public CustomSpecs<T> _and() {
        assert(valueList.size() == boolOps.size());
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if(!boolOps.empty()) {
            boolOps.pop();
            boolOps.push(Operator.AND);
        }
        return this;
    }

    public CustomSpecs<T> _or() {
        assert(valueList.size() == boolOps.size());
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if(!boolOps.empty()) {
            boolOps.pop();
            boolOps.push(Operator.OR);
        }
        return this;
    }

    public CustomSpecs<T> _notEqual(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.NOT_EQUAL);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _equal(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.EQUAL);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _like(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.LIKE);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _notLike(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.NOT_LIKE);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _isNotNull(String fieldKey) {
        if (fieldKey == null) return this;
        fieldList.push(fieldKey);
        valueList.push(new Object());
        compOpList.push(Operator.Is_Not_Null);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _greaterThan(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.GREATER_THAN);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _lessThan(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.LESS_THAN);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

    public CustomSpecs<T> _in(String fieldKey, Object fieldValue) {
        if (fieldValue == null || fieldValue == null) return this;
        fieldList.push(fieldKey);
        valueList.push(fieldValue);
        compOpList.push(Operator.IN);
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        if (boolOps.size() < fieldList.size()) 
        boolOps.push(Operator.AND);
        return this;
    }

        // public CustomSpecs<T> _isNotEmpty(String fieldKey) {
    //     if (fieldKey == null) return this;
    //     fieldList.push(fieldKey);
    //     valueList.push(new Object());
    //     compOpList.push(Operator.Is_Not_Empty);
    //     assert(fieldList.size() == valueList.size());
    //     assert(compOpList.size() == fieldList.size());
    //     if (boolOps.size() < fieldList.size()) 
    //     boolOps.push(Operator.AND);
    //     return this;
    // }

    public Specification<T> _generateSpecifications() {
        Specification<T> combinedSpec = Specification.where(null);
        assert(valueList.size() == boolOps.size());
        assert(fieldList.size() == valueList.size());
        assert(compOpList.size() == fieldList.size());
        while (!boolOps.isEmpty() && !valueList.isEmpty() && !fieldList.isEmpty()) {
            Operator boolOp = boolOps.pop();
            Object fieldValue = valueList.pop();
            String fieldKey = fieldList.pop();
            Operator compOp = compOpList.pop();
            Specification<T> _tmpSpec =  _toPredicate(fieldKey, fieldValue, compOp);
            if (_tmpSpec == null) 
            throw new UnsupportedOperationException("Unsupported toPredicate");
            switch (boolOp) {
            case OR:
                combinedSpec = _tmpSpec.or(combinedSpec);
                break;
            case AND:
                default:
                    combinedSpec = _tmpSpec.and(combinedSpec);
                    break;
                }
            }
            assert(valueList.size() == boolOps.size());
            assert(fieldList.size() == valueList.size());
            assert(compOpList.size() == fieldList.size());
            return combinedSpec;
    }

    enum Operator {

        EQUAL, NOT_EQUAL, Is_Not_Null, LIKE, NOT_LIKE, Is_Not_Empty, GREATER_THAN, LESS_THAN, IN, AND, OR;
    }
}