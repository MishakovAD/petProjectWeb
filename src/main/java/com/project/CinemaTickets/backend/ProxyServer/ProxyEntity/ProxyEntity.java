package com.project.CinemaTickets.backend.ProxyServer.ProxyEntity;

public class ProxyEntity {
    private int proxyEntity_id;
    private String ip_address;
    private String port;
    private String type;

    public ProxyEntity() {
    }

    public ProxyEntity(String ip_address, String port, String type) {
        this.ip_address = ip_address;
        this.port = port;
        this.type = type;
    }

    public int getProxyEntity_id() {
        return proxyEntity_id;
    }

    public void setProxyEntity_id(int proxyEntity_id) {
        this.proxyEntity_id = proxyEntity_id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProxyEntity{" +
                "proxyEntity_id=" + proxyEntity_id +
                ", ip_address='" + ip_address + '\'' +
                ", port='" + port + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
