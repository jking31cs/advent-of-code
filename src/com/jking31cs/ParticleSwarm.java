package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import javafx.util.Pair;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ParticleSwarm {

    private static class Vector {
        private int x,y,z = 0;

        private Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private int distFromOrigin() {
            return abs(x) + abs(y) + abs(z);
        }

        private double distFromPoint(Vector v) {
            return sqrt(pow(x - v.x, 2) + pow(y-v.y, 2) + pow(z-v.z, 2));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Vector vector = (Vector) o;
            return x == vector.x &&
                    y == vector.y &&
                    z == vector.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    static class Particle {
        private int id;
        private Vector p,v,a;

        Particle(int id) {
            this.id = id;
        }

        void update() {
            v.x += a.x;
            v.y += a.y;
            v.z += a.z;
            p.x += v.x;
            p.y += v.y;
            p.z += v.z;
        }
    }

    static List<Particle> parseFile(File file) throws FileNotFoundException {
        List<Particle> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            int id = 0;
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] info = s.split(", ");
                String p = info[0].trim().substring(2);
                String v = info[1].trim().substring(2);
                String a = info[2].trim().substring(2);

                p = p.substring(1, p.length()-1);
                v = v.substring(1, v.length()-1);
                a = a.substring(1, a.length()-1);
                Particle part = new Particle(id);
                String[] posBits = p.split(",");
                part.p = new Vector(
                        Integer.parseInt(posBits[0].trim()),
                        Integer.parseInt(posBits[1].trim()),
                        Integer.parseInt(posBits[2].trim())
                );
                String[] vecBits = v.split(",");
                part.v = new Vector(
                        Integer.parseInt(vecBits[0].trim()),
                        Integer.parseInt(vecBits[1].trim()),
                        Integer.parseInt(vecBits[2].trim())
                );
                String[] accBits = a.split(",");
                part.a = new Vector(
                        Integer.parseInt(accBits[0].trim()),
                        Integer.parseInt(accBits[1].trim()),
                        Integer.parseInt(accBits[2].trim())
                );
                toRet.add(part);
                id+=1;
            }
        }
        return toRet;
    }

    static int collisionDetection(List<Particle> particles) {
        Map<Pair<Integer, Integer>, Boolean> couldCollide = new HashMap<>();
        for (int i = 0; i < particles.size(); i++) {
            for (int j = i+1; j < particles.size(); j++) {
                couldCollide.put(new Pair<>(i,j), true);
            }
        }
        Map<Integer, Particle> particleLookup = new HashMap<>();
        for (Particle p : particles) particleLookup.put(p.id, p);

        //First, look to verify there's no collisions at the start
        Set<Integer> collided = new HashSet<>();
        while (couldCollide.values().stream().anyMatch(aBoolean -> aBoolean)) {
            Map<Pair<Integer, Integer>, Double> currentDistances = new HashMap<>(couldCollide.size());
            for (Pair<Integer, Integer> pair : couldCollide.keySet()) {
                int i = pair.getKey();
                int j = pair.getValue();
                Particle pi = particleLookup.get(i);
                Particle pj = particleLookup.get(j);
                currentDistances.put(new Pair<>(i,j), pi.p.distFromPoint(pj.p));
            }
            for (Particle p : particleLookup.values()) p.update();
            for (Pair<Integer, Integer> pair : couldCollide.keySet()) {
                int i = pair.getKey();
                int j = pair.getValue();
                Particle pi = particleLookup.get(i);
                Particle pj = particleLookup.get(j);
                double newDist = pi.p.distFromPoint(pj.p);
                if (newDist == 0d) {
                    collided.add(pi.id);
                    collided.add(pj.id);
                    couldCollide.put(new Pair<>(i,j), false);
                } else if (newDist > currentDistances.get(new Pair<>(i,j))) {
                    couldCollide.put(new Pair<>(i,j), false);
                }
            }
            Set<Pair<Integer, Integer>> toRemove = new HashSet<>();
            for (Pair<Integer, Integer> pair : couldCollide.keySet()) {
                if (!couldCollide.get(pair)) toRemove.add(pair);
            }
            for (Pair<Integer, Integer> pair : toRemove) couldCollide.remove(pair);
        }
        return particles.size() - collided.size();
    }

    static int closestLongTerm(List<Particle> particles) {
        Map<Integer, Boolean> hasPeaked = new HashMap<>(particles.size());
        for (Particle p : particles) hasPeaked.put(p.id, false);

        //Now make all particles peak so that they all start moving away from origin.
        while (!hasPeaked.values().stream().allMatch(aBoolean -> aBoolean)) {
            for (Particle p : particles) {
                Vector curV = new Vector(p.v.x, p.v.y, p.v.z);
                p.update();
                if (p.v.distFromOrigin() > curV.distFromOrigin()) {
                    hasPeaked.put(p.id, true);
                }
            }
        }

        for (int i = 0; i < 1000; i++) {
            for (Particle p : particles) p.update();
        }
        int leastDist = Integer.MAX_VALUE;
        int id = -1;
        for (Particle p : particles) {
            if (p.p.distFromOrigin() < leastDist) {
                leastDist = p.p.distFromOrigin();
                id = p.id;
            }
        }
        return id;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(collisionDetection(parseFile(new File("day20TestFile.txt"))));
    }
}
