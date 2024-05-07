package com.esig.selecao.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.esig.selecao.exception.AppException;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

@Component
//criei essa classe para isolar a verificação de quais campos estão nulos
public class Patcher{

    public void patchProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public void copiarPropriedadesNaoNulas(Object objetoA, Object objetoB) {
    Class<?> classeA = objetoA.getClass();
    Class<?> classeB = objetoB.getClass();

    try {
        for (Field campoA : classeA.getDeclaredFields()) {
            if (!campoA.isSynthetic() && !Modifier.isStatic(campoA.getModifiers())) {
                String nomeCampo = campoA.getName();
                Field campoB = classeB.getDeclaredField(nomeCampo);
    
                if (campoB.getGenericType().equals(campoA.getGenericType())) {
                    campoA.setAccessible(true);
                    campoB.setAccessible(true);
    
                    Object valorCampoA = campoA.get(objetoA);
                    if (valorCampoA != null) {
                        campoB.set(objetoB, valorCampoA);
                    }
                }
            }
        }
    } catch (Exception e) {
        throw new AppException("patch error", HttpStatus.BAD_REQUEST);
    }
}

}
