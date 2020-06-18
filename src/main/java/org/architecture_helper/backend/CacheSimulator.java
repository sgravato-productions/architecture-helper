package org.architecture_helper.backend;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CacheSimulator {
    public boolean debug = false;

    //private fields
    private List<LRU> lrus;
    private List<List<Integer>> cacheSets;
    private int blockSize;
    private int blocks;
    private int ways;

    //constructor
    public CacheSimulator(int blocks, int blockSize, int ways) {
        this.blocks = blocks;
        this.blockSize = blockSize;
        this.ways = ways;

        cacheSets = IntStream.range(0,blocks).boxed().map(i -> IntStream.range(0,ways).map(index -> -1).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        lrus = IntStream.range(0,blocks).boxed().map(i -> new LRU(ways)).collect(Collectors.toList());
    }


    public Object[] find(int address) {
        //calculating memory block and cache block index
        int memBlock = calculateMemBlock(address);
        int index = calculateCacheSet(-1, memBlock);

        List<Integer> cacheSet = cacheSets.get(index);

        //searching the memory block inside the cache block
        int lru = -1;
        boolean hit = true;
        if (cacheSet.contains(memBlock)) {
            //HIT, updating LRU before returning
            lru = cacheSet.indexOf(memBlock);
            if (debug) System.out.println("{address}: HIT (MEMBLOCK "+memBlock+" IS IN cacheSet "+index+"["+lru+"])");
        }else {
            //MISS, NEED TO LOAD BLOCK
            lru = lrus.get(index).getLeastRecentlyUsed();
            cacheSet.set(lru, memBlock);
            if (debug) System.out.println(address+": MISS!!! MEMBLOCK "+memBlock+" -> cacheSet "+index+"["+lru+"]");
            hit = false;
        }

        //updating LRU
        lrus.get(index).pushToTop(lru);
        if (debug) System.out.println("LAST USED BLOCK FOR cacheSet "+index+": {"+lru+"}");

        return new Object[]{hit, memBlock, index, lru};
    }

    public boolean hitOrMiss(int address) {
        return (boolean)find(address)[0];
    }

    public int calculateMemBlock(int address){
        return address / blockSize;
    }

    public int calculateCacheSet(int address, int memBlock) {
        if (address != -1) {
            memBlock = calculateMemBlock(address);
        }
        return memBlock % blocks;
    }


}