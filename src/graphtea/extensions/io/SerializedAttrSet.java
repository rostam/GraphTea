package graphtea.extensions.io;

import graphtea.platform.attribute.AttributeSet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * it filters all unserializable attributes of the binding attribute set
 * @author Azin Azadi
 */
public class SerializedAttrSet<Binding extends AttributeSet> implements Serializable {

    public HashMap<String, Serializable> attrs = new HashMap<>();

    public SerializedAttrSet(Binding binding) {
        setBinding(binding);
    }

    /**
     * feed the binding with the values
     */
    public void feed(Binding binding){
        for (String key : attrs.keySet()){
            binding.put(key, attrs.get(key));
        }
    }
    /**
     * @return a unmodifiable copy of attributes in this object
     */
    public void setBinding(Binding binding) {
        attrs.clear();
        Map<String, Object> bindingAttrs = binding.getAttrs();
        for (String key : bindingAttrs.keySet()) {
            Object val = bindingAttrs.get(key);
            if (val instanceof Serializable) {
                attrs.put(key, (Serializable) val);
            }
//            else
//                System.out.println(key + "Is not serializable! --wouldnt save!");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof SerializedAttrSet) )  return false;
        return attrs.equals(((SerializedAttrSet) obj).attrs);
    }




}
