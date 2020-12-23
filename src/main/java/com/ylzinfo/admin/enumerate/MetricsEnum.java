package com.ylzinfo.admin.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author licl
 * @date 2020/11/11
 */
@SuppressWarnings({"ALL", "AlibabaEnumConstantsMustHaveComment"})
@AllArgsConstructor
@Getter
public enum MetricsEnum {

    JVM_Memory_Size("虚拟机内存大小","/jvm.memory.committed"),

    Heap_Size("非堆内存大小","/jvm.memory.committed?tag=area:nonheap"),

    No_Heap_Size("堆内存大小","/jvm.memory.committed?tag=area:heap"),

    PS_Eden_Space_Size("PS_Eden_Space区大小","/jvm.memory.committed?tag=area:heap,id:PS+Eden+Space"),

    PS_Survivor_Space_Size("PS_Survivor_Space区大小","/jvm.memory.committed?tag=area:heap,id:PS+Survivor+Space"),

    PS_Old_Space_Size("PS_Old_Space区大小","/jvm.memory.committed?tag=area:heap,id:PS+Old+Gen"),

    No_Heap_Size_Used("已使用非堆内存大小","/jvm.memory.used?tag=area:nonheap"),

    Heap_Size_Used("已使用堆内存大小","/jvm.memory.used?tag=area:heap"),

    PS_Eden_Space_Size_Used("已使用PS_Eden_Space区大小","/jvm.memory.used?tag=area:heap,id:PS+Eden+Space"),

    PS_Survivor_Space_Size_Used("已使用PS_Survivor_Space区大小","/jvm.memory.used?tag=area:heap,id:PS+Survivor+Space"),

    PS_Old_Space_Size_Used("已使用PS_Old_Space区大小","/jvm.memory.used?tag=area:heap,id:PS+Old+Gen"),

    Process_Cpu_Usage("jvm进程cpu使用率","/process.cpu.usage"),

    System_Cpu_Usage("系统cpu使用率","/system.cpu.usage");




    private String description;
    private String uri;
}
