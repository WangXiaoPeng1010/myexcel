/*
 * Copyright 2019 liaochong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.myexcel.core.converter;

/**
 * 转换接口，所有用户自定义转换均基于该接口
 *
 * @author liaochong
 * @version 1.0
 */
public interface Converter<E, T> {

    /**
     * E 转换为 T
     *
     * @param obj obj
     * @return T
     */
    T covert(E obj);
}
