/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

import grails.converters.JSON

import java.util.Date;

import edu.hawaii.its.dcmd.inf.*
import grails.util.GrailsUtil
import edu.hawaii.its.dcmd.inf.ResourceAllocation
import edu.hawaii.its.dcmd.inf.Application
//import edu.hawaii.its.dcmd.inf.ContactInfo
import edu.hawaii.its.dcmd.inf.Environment
import edu.hawaii.its.dcmd.inf.User
import edu.hawaii.its.dcmd.inf.Status
import edu.hawaii.its.dcmd.inf.PowerSource
import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->

        println "Running BootStrap.groovy"
        environments {

             production {
                def ROLE_USER
                if (!Role.count()) {
                    ROLE_USER = new Role(authority: 'ROLE_USER')
                    ROLE_USER.save(failOnError:true,flush:true)
                }

                 def defaultSettings = new Uisettings(themeVal:1,header:1,background:1,font:1).save(failOnError: true,flush:true)

                def userKarsin, userElfalan, userHodges, kawachi, ckawano, jonathan, czane, steven, garry, thang, romeot,
                    anicasm, nakadoma, vyoshida, osamum, kc96813, userKylanh
                if(!User.count()) {
                    userKarsin = new User(username: 'karsin', password:'none', enabled: true)
                    userKarsin.save(failOnError:true,flush:true)
                    userElfalan = new User(username: 'elfalan', password:'none', enabled: true)
                    userElfalan.save(failOnError:true,flush:true)
                    userHodges = new User(username: 'mhodges', password:'none', enabled: true)
                    userHodges.save(failOnError:true,flush:true)
                    kawachi= new User(username: 'kawachi', password:'none', enabled: true)
                    kawachi.save(failOnError:true,flush:true)
                    ckawano= new User(username: 'ckawano', password:'none', enabled: true)
                    ckawano.save(failOnError:true,flush:true)
                    jonathan= new User(username: 'jonathan', password:'none', enabled: true)
                    jonathan.save(failOnError:true,flush:true)
                    czane= new User(username: 'czane', password:'none', enabled: true)
                    czane.save(failOnError:true,flush:true)
                    steven= new User(username: 'steven', password:'none', enabled: true)
                    steven.save(failOnError:true,flush:true)
                    garry= new User(username: 'garry', password:'none', enabled: true)
                    garry.save(failOnError:true,flush:true)
                    thang= new User(username: 'thang', password:'none', enabled: true)
                    thang.save(failOnError:true,flush:true)
                    romeot= new User(username: 'romeot', password:'none', enabled: true)
                    romeot.save(failOnError:true,flush:true)
                    anicasm= new User(username: 'anicasm', password:'none', enabled: true)
                    anicasm.save(failOnError:true,flush:true)
                    nakadoma= new User(username: 'nakadoma', password:'none', enabled: true)
                    nakadoma.save(failOnError:true,flush:true)
                    vyoshida= new User(username: 'vyoshida', password:'none', enabled: true)
                    vyoshida.save(failOnError:true,flush:true)
                    osamum= new User(username: 'osamum', password:'none', enabled: true)
                    osamum.save(failOnError:true,flush:true)
                    kc96813= new User(username: 'kc96813', password:'none', enabled: true)
                    kc96813.save(failOnError:true,flush:true)
                    userKylanh = new User(username: 'kylanh', password:'none', enabled: true)
                    userKylanh.save(failOnError:true,flush:true)

                }
                def rolesKarsin, rolesElfalan, rolesHodges, rolesKawachi, rolesCkawano, rolesJonathan, rolesCzane,
                    rolesSteven, rolesGarry, rolesThang, rolesRomeot, rolesAnicasm, rolesNakadoma, rolesVyoshida,
                    rolesOsamum, rolesKc96813, rolesKylanh
                if(!UserRole.count()) {
                    rolesKarsin = new UserRole(user: userKarsin, role: ROLE_USER)
                    rolesKarsin.save(failOnError:true,flush:true)
                    rolesElfalan = new UserRole(user: userElfalan, role: ROLE_USER)
                    rolesElfalan.save(failOnError:true,flush:true)
                    rolesHodges = new UserRole(user: userHodges, role: ROLE_USER)
                    rolesHodges.save(failOnError:true,flush:true)
                    rolesKawachi= new UserRole(user: kawachi, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesCkawano= new UserRole(user: ckawano, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesJonathan= new UserRole(user: jonathan, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesCzane= new UserRole(user: czane, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesSteven= new UserRole(user: steven, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesGarry= new UserRole(user: garry, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesThang= new UserRole(user: thang, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesRomeot= new UserRole(user: romeot, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesAnicasm= new UserRole(user: anicasm, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesNakadoma= new UserRole(user: nakadoma, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesVyoshida= new UserRole(user: vyoshida, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesOsamum= new UserRole(user: osamum, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesKc96813= new UserRole(user: kc96813, role: ROLE_USER).save(failOnError:true,flush:true)
                    rolesKylanh= new UserRole(user: userKylanh, role: ROLE_USER).save(failOnError:true,flush:true)

                }

                def dev, test, prod, qa, baseline, train, seed
                def pprod
                if (!Environment.count()) {
                    seed = new Environment( name: 'seed', abbreviation: 'seed' ).save( failOnError: true, flush: true )

                    prod = new Environment( name: 'production', abbreviation: 'prod' ).save( failOnError: true, flush: true )
                    pprod = new Environment( name: 'pre-production', abbreviation: 'pre-prod' ).save( failOnError: true, flush: true )
                    test = new Environment( name: 'test', abbreviation: 'test' ).save( failOnError: true, flush: true )
                    dev  = new Environment( name: 'development', abbreviation: 'dev' ).save( failOnError: true, flush: true )
                    qa = new Environment(name: 'Quality Assurance', abbreviation: 'qa').save( failOnError: true, flush: true )
                    baseline = new Environment(name: 'Baseline', abbreviation: 'baseline').save( failOnError: true, flush: true )
                    train = new Environment(name: 'Training', abbreviation: 'train').save( failOnError: true, flush: true )
                }

                if (!UnitType.count()){
                    def gb = new UnitType(
                            unit: "GB",
                            unitDescription: "Gigabyte",
                            updatedById: 1,
                    )
                    gb.save(failOnError: true, flush: true)

                    def mb = new UnitType(
                            unit: "MB",
                            unitDescription: "Megabyte",
                            updatedById: 1,
                    )
                    mb.save(failOnError: true, flush: true)

                    def oneItem = new UnitType(
                            unit: "Item",
                            unitDescription: "a unit of a group of items",
                            updatedById: 1,
                    )
                    oneItem.save(failOnError: true, flush: true)

                    def cpuThread = new UnitType(
                            unit: "CPU Threads",
                            unitDescription: "a CPU thread",
                            updatedById: 1,
                    )
                    cpuThread.save(failOnError: true, flush: true)

                    def cpuCore = new UnitType(
                            unit: "CPU Cores",
                            unitDescription: "a CPU core",
                            updatedById: 1,
                    )
                    cpuCore.save(failOnError: true, flush: true)

                }

                def server, diskArray, cert, rack, misc
                if (!AssetType.count()){
                    server = new AssetType(
                            name: 'Server - Hardware',
                            description: 'Hardware that consists of one or more servers',
                            abbreviation: 'Server'
                    )
                    server.save(failOnError: true, flush: true)

                    diskArray = new AssetType(
                            name: 'Disk Array',
                            description: 'A disk array',
                            abbreviation: 'Disk'
                    )
                    diskArray.save(failOnError: true, flush: true)

                    cert = new AssetType(
                            name: 'SSL Certificate',
                            description: 'SSL Certificate',
                            abbreviation: 'Cert'
                    )
                    cert.save(failOnError: true, flush: true)

                    rack = new AssetType(
                            name: 'Server Rack',
                            description: 'A rack that stores servers and other hardware',
                            abbreviation: 'Rack'
                    )
                    rack.save(failOnError: true, flush:true)

                    misc = new AssetType(
                            name: 'Miscellaneous Asset',
                            description: 'Any miscellaneous asset',
                            abbreviation: 'Misc'
                    )
                    misc.save(failOnError: true, flush:true)
                }

                def locale1, locale2, locale3, locale4
                if ( ! Location.count() ) {
                    locale1 = new Location( locationDescription: "Banner Conference Room", updatedById: 1 ).save(failOnError: true, flush: true)
                    locale2 = new Location( locationDescription: "Keller 103", updatedById: 1 ).save(failOnError: true, flush: true)
                    locale3 = new Location( locationDescription: "Campus Center 304", updatedById: 1).save(failOnError: true, flush: true)
                    locale4 = new Location( locationDescription: "New ITS Building", updatedById: 1).save(failOnError: true, flush:true)
                }

                def solarisCluster, standalone, vmcluster, vmcluster2
                if ( ! Cluster.count() ) {
                    vmcluster = new Cluster(name: "VMware Cluster" )
                    vmcluster.save(failOnError:true, flush:true)
                    vmcluster2 = new Cluster(name: "VMware Cluster2" )
                    vmcluster2.save(failOnError:true, flush:true)
                    standalone = new Cluster(name: "Standalone")
                    standalone.save(failOnError:true, flush:true)
                    solarisCluster = new Cluster(name: "Solaris Global Zones")
                    solarisCluster.save(failOnError:true, flush:true)
                }


            }

            test {

                JSON.registerObjectMarshaller(Host) {
                    def returnArray = [:]
                    returnArray['Hostname'] = it.hostname
                    returnArray['DCmd_ID'] = it.id
                    returnArray['Environment'] = it.env?.abbreviation
                    returnArray['Status'] = it.status?.abbreviation
                    returnArray['Type'] = it.type
                    returnArray['PhysicalServer'] = it.asset?.itsId
                    returnArray['OS'] = it.os
                    returnArray['Services'] = it.tiers
                    returnArray['SupportStaff'] = it.supporters

                    return returnArray
                }

                JSON.registerObjectMarshaller(SupportRole) {
                    def returnArray = [:]
                    returnArray['Role'] = it.roleName?.roleName
                    returnArray['Person'] = it.person?.uhName
                    return returnArray
                }

                JSON.registerObjectMarshaller(Tier){
                    System.out.println(it.instanceDependencies?.first()?.service?.serviceTitle)

                    def returnArray = [:]
                    returnArray['ServiceName'] = it.instanceDependencies?.first()?.service?.serviceTitle
                    returnArray['Application'] = it.mainApp
                    return returnArray

                }

                JSON.createNamedConfig('applicationAll') {
                    it.registerObjectMarshaller(Application) { Application app, JSON json ->
                        def returnArray = [:]
                        returnArray['AppName'] = app.applicationTitle
                        returnArray['Environment'] = app.env?.abbreviation
                        returnArray['Services'] = app.services
                        return returnArray
                    }
                }

                JSON.registerObjectMarshaller(Application) {
                    def returnArray = [:]
                    returnArray['AppName'] = it.applicationTitle
                    returnArray['Environment'] = it.env?.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(RoleType) {
                    def returnArray = [:]
                    returnArray['Name'] = it.roleName
                    return returnArray
                }

                JSON.registerObjectMarshaller(Environment) {
                    def returnArray = [:]
                    returnArray['Name'] = it.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Status) {
                    def returnArray = [:]
                    returnArray['Name'] = it.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Person) {
                    def returnArray = [:]
                    returnArray['uhName'] = it.uhName
                    return returnArray
                }

                JSON.createNamedConfig('personAll') {
                    it.registerObjectMarshaller(Person) { Person person, JSON json ->
                        def returnArray = [:]
                        returnArray['uhName'] = person.uhName
                        returnArray['firstName'] = person.firstName
                        returnArray['lastName'] = person.lastName
                        returnArray['uhNumber'] = person.uhNumber
                        return returnArray
                    }
                }

                JSON.registerObjectMarshaller(Service) {
                    def returnArray = [:]
                    returnArray['ServiceName'] = it.serviceTitle
                    returnArray['Environment'] = it.env?.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Cluster) {
                    def returnArray = [:]
                    returnArray['Name'] = it.name
                    return returnArray
                }
                     /*
                def powerType
                powerType = new AssetType(
                        name: 'Power',
                        description: 'Power entities',
                        abbreviation: 'Power'
                )
                powerType.save(failOnError:true)

                def PSU1
                if(!PowerSource.count()) {
                    PSU1 = new PowerSource(itsId:'PSU1', capacity:100, assetType:powerType).save(failOnError:true)
                }

                def Bus1
                if(!PowerPanel.count()) {
                    Bus1 = new PowerPanel(itsId:'Bus1', capacity:50, source:PSU1, assetType:powerType, breaker_poles:16).save(failOnError:true)
                }
                def Breaker1
                if(!PowerBreaker.count()) {
                    Breaker1 = new PowerBreaker(itsId:'Breaker1', label: 'A01-a', capacity:30, panel:Bus1, assetType:powerType, voltage: 110).save(failOnError:true)
                }
                def connector1
                if (! PowerConnector.count() ) {
                    connector1 = new PowerConnector(name:'C1').save(failOnError: true, flush:true)
                }

                def PSType1
                if (! PowerStripType.count()) {
                    PSType1 = new PowerStripType(name:'PSType1', connectors: [connector1]).save(failOnError: true, flush:true)
                }

                def strip1, strip2, strip3
                if(!PowerStrip.count()) {
                    strip1 = new PowerStrip(itsId:'XY', capacity:10, breaker:Breaker1, powerUsed:6, assetType:powerType, type: PSType1).save(failOnError:true)
                    strip2 = new PowerStrip(itsId:'XZ', capacity:10, breaker:Breaker1, powerUsed:10, assetType:powerType, type: PSType1).save(failOnError:true)
                    strip3 = new PowerStrip(itsId:'YZ', capacity:10, breaker:Breaker1, powerUsed:10, assetType:powerType).save(failOnError:true)
                }
                */
            }

            development {

                JSON.registerObjectMarshaller(Host) {
                    def returnArray = [:]
                    returnArray['Hostname'] = it.hostname
                    returnArray['DCmd_ID'] = it.id
                    returnArray['Environment'] = it.env?.abbreviation
                    returnArray['Status'] = it.status?.abbreviation
                    returnArray['Type'] = it.type
                    returnArray['PhysicalServer'] = it.asset?.itsId
                    returnArray['OS'] = it.os
                    returnArray['Services'] = it.tiers
                    returnArray['SupportStaff'] = it.supporters

                    return returnArray
                }

                JSON.registerObjectMarshaller(SupportRole) {
                    def returnArray = [:]
                    returnArray['Role'] = it.roleName?.roleName
                    returnArray['Person'] = it.person?.uhName
                    return returnArray
                }

                JSON.registerObjectMarshaller(Tier){
                    System.out.println(it.instanceDependencies?.first()?.service?.serviceTitle)

                    def returnArray = [:]
                    returnArray['ServiceName'] = it.instanceDependencies?.first()?.service?.serviceTitle
                    returnArray['Application'] = it.mainApp
                    return returnArray

                }

                JSON.createNamedConfig('applicationAll') {
                    it.registerObjectMarshaller(Application) { Application app, JSON json ->
                        def returnArray = [:]
                        returnArray['AppName'] = app.applicationTitle
                        returnArray['Environment'] = app.env?.abbreviation
                        returnArray['Services'] = app.services
                        return returnArray
                    }
                }

                JSON.registerObjectMarshaller(Application) {
                    def returnArray = [:]
                    returnArray['AppName'] = it.applicationTitle
                    returnArray['Environment'] = it.env?.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(RoleType) {
                    def returnArray = [:]
                    returnArray['Name'] = it.roleName
                    return returnArray
                }

                JSON.registerObjectMarshaller(Environment) {
                    def returnArray = [:]
                    returnArray['Name'] = it.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Status) {
                    def returnArray = [:]
                    returnArray['Name'] = it.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Person) {
                    def returnArray = [:]
                    returnArray['uhName'] = it.uhName
                    return returnArray
                }

                JSON.createNamedConfig('personAll') {
                    it.registerObjectMarshaller(Person) { Person person, JSON json ->
                        def returnArray = [:]
                        returnArray['uhName'] = person.uhName
                        returnArray['firstName'] = person.firstName
                        returnArray['lastName'] = person.lastName
                        returnArray['uhNumber'] = person.uhNumber
                        return returnArray
                    }
                }

                JSON.registerObjectMarshaller(Service) {
                    def returnArray = [:]
                    returnArray['ServiceName'] = it.serviceTitle
                    returnArray['Environment'] = it.env?.abbreviation
                    return returnArray
                }

                JSON.registerObjectMarshaller(Cluster) {
                    def returnArray = [:]
                    returnArray['Name'] = it.name
                    return returnArray
                }

                def ROLE_READ, ROLE_WRITE, ROLE_ADMIN, ROLE_USER
                if (!Role.count()) {
                    ROLE_READ = new Role(authority: 'ROLE_READ')
                    ROLE_READ.save(failOnError:true,flush:true)

                    ROLE_WRITE = new Role(authority: 'ROLE_WRITE')
                    ROLE_WRITE.save(failOnError:true,flush:true)

                    ROLE_ADMIN = new Role(authority: 'ROLE_ADMIN')
                    ROLE_ADMIN.save(failOnError:true, flush:true)

                    ROLE_USER = new Role(authority: 'ROLE_USER')
                    ROLE_USER.save(failOnError:true, flush:true)
                }

                def defaultSettings = new Uisettings(themeVal:1,header:1,background:1,font:11).save(failOnError: true,flush:true)


                def userKarsin, userElfalan, userHodges, kawachi, ckawano, jonathan, czane, steven, garry, thang, romeot,
                    anicasm, nakadoma, vyoshida, osamum, kc96813, userKylanh, userJshima
                if(!User.count()) {
                    userKarsin = new User(username: 'karsin', password:'none', enabled: true, userSettings: defaultSettings)
                    userKarsin.save(failOnError:true,flush:true)
                    userElfalan = new User(username: 'elfalan', password:'none', enabled: true, userSettings: defaultSettings)
                    userElfalan.save(failOnError:true,flush:true)
                    userHodges = new User(username: 'mhodges', password:'none', enabled: true, userSettings: defaultSettings)
                    userHodges.save(failOnError:true,flush:true)
                    kawachi= new User(username: 'kawachi', password:'none', enabled: true, userSettings: defaultSettings)
                    kawachi.save(failOnError:true,flush:true)
                    ckawano= new User(username: 'ckawano', password:'none', enabled: true, userSettings: defaultSettings)
                    ckawano.save(failOnError:true,flush:true)
                    jonathan= new User(username: 'jonathan', password:'none', enabled: true, userSettings: defaultSettings)
                    jonathan.save(failOnError:true,flush:true)
                    czane= new User(username: 'czane', password:'none', enabled: true, userSettings: defaultSettings)
                    czane.save(failOnError:true,flush:true)
                    steven= new User(username: 'steven', password:'none', enabled: true, userSettings: defaultSettings)
                    steven.save(failOnError:true,flush:true)
                    garry= new User(username: 'garry', password:'none', enabled: true, userSettings: defaultSettings)
                    garry.save(failOnError:true,flush:true)
                    thang= new User(username: 'thang', password:'none', enabled: true, userSettings: defaultSettings)
                    thang.save(failOnError:true,flush:true)
                    romeot= new User(username: 'romeot', password:'none', enabled: true, userSettings: defaultSettings)
                    romeot.save(failOnError:true,flush:true)
                    anicasm= new User(username: 'anicasm', password:'none', enabled: true, userSettings: defaultSettings)
                    anicasm.save(failOnError:true,flush:true)
                    nakadoma= new User(username: 'nakadoma', password:'none', enabled: true, userSettings: defaultSettings)
                    nakadoma.save(failOnError:true,flush:true)
                    vyoshida= new User(username: 'vyoshida', password:'none', enabled: true, userSettings: defaultSettings)
                    vyoshida.save(failOnError:true,flush:true)
                    osamum= new User(username: 'osamum', password:'none', enabled: true, userSettings: defaultSettings)
                    osamum.save(failOnError:true,flush:true)
                    kc96813= new User(username: 'kc96813', password:'none', enabled: true, userSettings: defaultSettings)
                    kc96813.save(failOnError:true,flush:true)
                    userKylanh = new User(username: 'kylanh', password:'none', enabled: true, userSettings: defaultSettings)
                    userKylanh.save(failOnError:true,flush:true)
                    userJshima = new User(username: 'jshima', password:'none', enabled: true, userSettings: defaultSettings)
                    userJshima.save(failOnError:true,flush:true)

                }
                def rolesKarsin, rolesElfalan, rolesHodges, rolesKawachi, rolesCkawano, rolesJonathan, rolesCzane,
                        rolesSteven, rolesGarry, rolesThang, rolesRomeot, rolesAnicasm, rolesNakadoma, rolesVyoshida,
                        rolesOsamum, rolesKc96813, rolesKylanh, rolesJshima
                if(!UserRole.count()) {
                    rolesKarsin = new UserRole(user: userKarsin, role: ROLE_ADMIN)
                    rolesKarsin.save(failOnError:true,flush:true)
                    rolesElfalan = new UserRole(user: userElfalan, role: ROLE_ADMIN)
                    rolesElfalan.save(failOnError:true,flush:true)
                    rolesHodges = new UserRole(user: userHodges, role: ROLE_READ)
                    rolesHodges.save(failOnError:true,flush:true)
                    rolesKawachi= new UserRole(user: kawachi, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesCkawano= new UserRole(user: ckawano, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesJonathan= new UserRole(user: jonathan, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesCzane= new UserRole(user: czane, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesSteven= new UserRole(user: steven, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesGarry= new UserRole(user: garry, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesThang= new UserRole(user: thang, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesRomeot= new UserRole(user: romeot, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesAnicasm= new UserRole(user: anicasm, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesNakadoma= new UserRole(user: nakadoma, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesVyoshida= new UserRole(user: vyoshida, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesOsamum= new UserRole(user: osamum, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesKc96813= new UserRole(user: kc96813, role: ROLE_READ).save(failOnError:true,flush:true)
                    rolesKylanh= new UserRole(user: userKylanh, role: ROLE_ADMIN).save(failOnError:true,flush:true)
                    rolesJshima= new UserRole(user: userJshima, role: ROLE_ADMIN).save(failOnError:true,flush:true)

                }

                def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)



//				}

                /*
                if(!Vendor.count()){
                    new Vendor(name: "Widgets, Inc.", phone:"1234567").save(failOnError:true)
                    new Vendor(name: "Acme Products Company", phone:"89101112").save(failOnError:true)
                    new Vendor(name: "Widgets 'R' Us", phone:"1314151").save(failOnError:true)
                }
                  */
                /*
                 * Set up a contract and all of its related objects.
                 */
                 /*
                def htr
                if(!HawaiiTaxRate.count()){
                    htr = new HawaiiTaxRate(rate:0.04500F, description:"Minimum Oahu Tax Rate")
                    htr.save(failOnError:true)
                    new HawaiiTaxRate(rate:0.04712F, description:"Maximum Oahu Tax Rate").save(failOnError:true,flush:true)
                }

                def csActive
                def csInactive
                if(!ContractStatus.count()){
                    csActive = new ContractStatus(status: "Active",	description: "Contract is currently active.")
                    csActive.save(failOnError:true)
                    csInactive = new ContractStatus(status: "Inactive",	description: "Contract is not currently active.")
                    csInactive.save(failOnError:true,flush:true)
                }

                def cft
                if (!ContractFeatureType.count()) {
                    cft = new ContractFeatureType(
                            type: "Hardware Maintenance",
                            description: "Contract provides for hardware maintenance, renewable annually."
                    )
                    cft.save(failOnError: true, flush: true)
                    cft = new ContractFeatureType(
                            type: "Hardware Procurement",
                            description: "Contract provides for discounted purchases of additional hardware."
                    )
                    cft.save(failOnError: true, flush: true)
                }

                def cft65, cft95
                if (!ContractFormType.count()) {
                    cft65 = new ContractFormType(
                            form: "Form 65",
                            description: "OPRPM Form 65 - Request for Sole PowerSource",
                            formUrl: "www.hawaii.edu/apis/apm/a8200/Forms/Form65.pdf"
                    )
                    cft65.save(failOnError: true, flush: true)
                    cft95 = new ContractFormType(
                            form: "Form 95",
                            description: "OPRPM Form 95 - Determination of Cost or Price Reasonableness",
                            formUrl: "www.hawaii.edu/apis/apm/a8200/Forms/Form95.pdf"
                    )
                    cft95.save(failOnError: true, flush: true)
                }

                def contract
                if(!Contract.count()){
                    contract = new Contract(
                            uhContractNo:"C100046",
                            vendorContractNo:"54546678",
                            uhContractTitle:"Install, Implement and Maintain a Storage Array",
                            contractStatus:csActive,
                            annualRenewalReminderMm:12,
                            annualRenewalReminderDd:30,
                            annualRenewalDeadlineMm:12,
                            annualRenewalDeadlineDd:30,
                            annualCost:0F,
                            contractBeginDate: df.parse("12/31/2011"),
                            contractEndDate: df.parse("12/30/2012"),
                            taxRate:htr
                    )
                    contract.addToRequiredRenewalForms(cft65)
                    contract.addToRequiredRenewalForms(cft95)
                    contract.save(failOnError:true)
                    new Contract(
                            uhContractNo:"AAE324",
                            vendorContractNo:"54AAAF6609",
                            uhContractTitle:"Build an app to end world hunger",
                            contractStatus:csInactive,
                            annualRenewalReminderMm:12,
                            annualRenewalReminderDd:30,
                            annualRenewalDeadlineMm:12,
                            annualRenewalDeadlineDd:30,
                            annualCost:0F,
                            contractBeginDate: df.parse("12/31/2011"),
                            contractEndDate: df.parse("12/30/2012"),
                            taxRate:htr
                    ).save(failOnError:true)
                }
                   */
                /*
                 *
                 */
                def dev, test, prod, qa, baseline, train, pprod, seed
                if (!Environment.count()) {
                    pprod = new Environment( name: 'pre-production', abbreviation: 'pre-prod' ).save( failOnError: true, flush: true )
                    seed = new Environment( name: 'seed', abbreviation: 'seed' ).save( failOnError: true, flush: true )
                    prod = new Environment( name: 'production', abbreviation: 'prod' ).save( failOnError: true, flush: true )
                    test = new Environment( name: 'test', abbreviation: 'test' ).save( failOnError: true, flush: true )
                    dev  = new Environment( name: 'development', abbreviation: 'dev' ).save( failOnError: true, flush: true )
                    qa = new Environment(name: 'Quality Assurance', abbreviation: 'qa').save( failOnError: true, flush: true )
                    baseline = new Environment(name: 'Baseline', abbreviation: 'baseline').save( failOnError: true, flush: true )
                    train = new Environment(name: 'Training', abbreviation: 'train').save( failOnError: true, flush: true )
                }

                def availStatus, problemStatus, maintStatus, offlineStatus
                if (!Status.count()) {
                    availStatus = new Status( abbreviation: 'Available')
                    availStatus.save(failOnError:true, flush:true)
                    problemStatus = new Status( abbreviation: 'Problem')
                    problemStatus.save(failOnError:true, flush:true)
                    maintStatus = new Status( abbreviation: 'Maintenance')
                    maintStatus.save(failOnError:true, flush:true)
                    offlineStatus = new Status( abbreviation: 'Offline')
                    offlineStatus.save(failOnError:true, flush:true)
                }


                def ramRes, swapRes, cpuCoreRes, cpuThreadRes, certRes, fcDiskRes, sataDiskRes, internalDiskRes
				if (!ResourceType.count()) {
					ramRes = new ResourceType(
							resourceType: "RAM",
							resourceDescription: "Random Access Memory",
							updatedById: 1,
							)
					ramRes.save(failOnError: true, flush: true)

					swapRes = new ResourceType(
							resourceType: "Swap",
							resourceDescription: "Swap space",
							updatedById: 1,
							)
					swapRes.save(failOnError: true, flush: true)

					cpuCoreRes = new ResourceType(
							resourceType: "CPU Core",
							resourceDescription: "CPU Core",
							updatedById: 1,
							)
					cpuCoreRes.save(failOnError: true, flush: true)

					cpuThreadRes = new ResourceType(
							resourceType: "CPU Thread",
							resourceDescription: "CPU Thread/vCPU",
							updatedById: 1,
							)
					cpuThreadRes.save(failOnError: true, flush: true)

					certRes = new ResourceType(
							resourceType: "SSL Certificate",
							resourceDescription: "SSL certificate",
							updatedById: 1,
							)
					certRes.save(failOnError: true, flush: true)

					fcDiskRes = new ResourceType(
							resourceType: "Fibre Channel Disk Space",
							resourceDescription: "Fibre Channel disk space",
							updatedById: 1,
							)
					fcDiskRes.save(failOnError: true, flush: true)

					sataDiskRes = new ResourceType(
							resourceType: "SATA Disk Space",
							resourceDescription: "SATA disk space",
							updatedById: 1,
							)
					sataDiskRes.save(failOnError: true, flush: true)

					internalDiskRes = new ResourceType(
							resourceType: "Internal Disk Space",
							resourceDescription: "Disk space inside a server",
							updatedById: 1,
							)
					internalDiskRes.save(failOnError: true, flush: true)

				}

                if (!UnitType.count()){
                    def gb = new UnitType(
                            unit: "GB",
                            unitDescription: "Gigabyte",
                            updatedById: 1,
                    )
                    gb.save(failOnError: true, flush: true)

                    def mb = new UnitType(
                            unit: "MB",
                            unitDescription: "Megabyte",
                            updatedById: 1,
                    )
                    mb.save(failOnError: true, flush: true)

                    def oneItem = new UnitType(
                            unit: "Item",
                            unitDescription: "a unit of a group of items",
                            updatedById: 1,
                    )
                    oneItem.save(failOnError: true, flush: true)

                    def cpuThread = new UnitType(
                            unit: "CPU Threads",
                            unitDescription: "a CPU thread",
                            updatedById: 1,
                    )
                    cpuThread.save(failOnError: true, flush: true)

                    def cpuCore = new UnitType(
                            unit: "CPU Cores",
                            unitDescription: "a CPU core",
                            updatedById: 1,
                    )
                    cpuCore.save(failOnError: true, flush: true)

                }

                def server, diskArray, cert, rack, misc, power
                if (!AssetType.count()){
                    server = new AssetType(
                            name: 'Server - Hardware',
                            description: 'Hardware that consists of one or more servers',
                            abbreviation: 'Server'
                    )
                    server.save(failOnError: true, flush: true)

                    diskArray = new AssetType(
                            name: 'Disk Array',
                            description: 'A disk array',
                            abbreviation: 'Disk'
                    )
                    diskArray.save(failOnError: true, flush: true)

                    cert = new AssetType(
                            name: 'SSL Certificate',
                            description: 'SSL Certificate',
                            abbreviation: 'Cert'
                    )
                    cert.save(failOnError: true, flush: true)

                    rack = new AssetType(
                            name: 'Server Rack',
                            description: 'A rack that stores servers and other hardware',
                            abbreviation: 'Rack'
                    )
                    rack.save(failOnError: true, flush:true)

                    misc = new AssetType(
                            name: 'Miscellaneous Asset',
                            description: 'Any miscellaneous asset',
                            abbreviation: 'Misc'
                    )
                    misc.save(failOnError: true, flush:true)
                    power = new AssetType(
                            name: 'Power Asset',
                            description: 'An Asset associated with the power system',
                            abbreviation: 'Power'
                    )
                    power.save(failOnError: true, flush:true)
                }

                def madeBy1, madeBy2, madeBy3
                if ( ! Manufacturer.count() ) {
                    madeBy1 = new Manufacturer( name: "Company A", code: "COA", phone: "555-AAAA", updatedById: 1 ).save(failOnError: true, flush: true)
                    madeBy2 = new Manufacturer( name: "Company B", code: "COB", phone: "555-BBBB", updatedById: 1 ).save(failOnError: true, flush: true)
                    madeBy3 = new Manufacturer( name: "Company C", code: "COC", phone: "555-CCCC", updatedById: 1 ).save(failOnError: true, flush: true)
                }

                def locale1, locale2, locale3
                if ( ! Location.count() ) {
                    locale1 = new Location( locationDescription: "Banner Conference Room", updatedById: 1 ).save(failOnError: true, flush: true)
                    locale2 = new Location( locationDescription: "Keller 103", updatedById: 1 ).save(failOnError: true, flush: true)
                    locale3 = new Location( locationDescription: "Campus Center 304", updatedById: 1).save(failOnError: true, flush: true)
                }
                def solarisCluster, standalone, vmcluster
                if ( ! Cluster.count() ) {
                    vmcluster = new Cluster(name: "VMware Cluster 2" )
                    vmcluster.save(failOnError:true, flush:true)
                    standalone = new Cluster(name: "Standalone")
                    standalone.save(failOnError:true, flush:true)
                    solarisCluster = new Cluster(name: "Solaris Global Zones")
                    solarisCluster.save(failOnError:true, flush:true)
                }

                def rack1, rack2
                if ( !Rack.count() ) {
                    rack1 = new Rack(itsId:"A1", rackNum:"A1", updatedById: 001, location: locale2, assetType: rack, manufacturer: madeBy1, ruCap: 45, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    rack1.Initialize()
                    rack1.save(failOnError: true, flush:true)
                    rack2 = new Rack(itsId:"A2", rackNum:"A2", updatedById: 001, location: locale2, assetType: rack, manufacturer: madeBy1, ruCap: 45, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    rack2.Initialize()
                    rack2.save(failOnError: true, flush:true)
                }

                def RU1, RU2, RU3, RU4, RU5, RU6
                if ( !RackUnit.count() ) {
                    RU1 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU1.save(failOnError: true, flush:true)
                    RU2 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU2.save(failOnError: true, flush:true)
                    RU3 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU3.save(failOnError: true, flush:true)
                    RU4 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU4.save(failOnError: true, flush:true)
                    RU5 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU5.save(failOnError: true, flush:true)
                    RU6 = new RackUnit(RUstatus:"Open", onRack:rack1 )
                    RU6.save(failOnError: true, flush:true)

                }


                def t5k99, t2k40, t2k41, t2k99, pub05, pub02, pub03, pvt02, aphrodite, apx01, t2k06, t2k07, t2k08, pvt08, VMCluster2, esx56, esx59
                if ( ! PhysicalServer.count() ) {
                    pvt08 = new PhysicalServer(cluster:solarisCluster, itsId: "pvt08", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, status: availStatus, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    pvt08.save(failOnError: true, flush:true)

                    VMCluster2 = new PhysicalServer(cluster:solarisCluster, itsId: "VM Cluster 2", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    VMCluster2.save(failOnError: true, flush:true)

                    t2k06 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k06", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k06.save(failOnError: true, flush:true)

                    t2k07 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k07", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k07.save(failOnError: true, flush:true)

                    t2k08 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k08", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k08.save(failOnError: true, flush:true)

                    t5k99 = new PhysicalServer(cluster:solarisCluster, itsId: "t5k99", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t5k99.save(failOnError: true, flush:true)

                    t2k40 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k40", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k40.save(failOnError: true, flush:true)

                    t2k41 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k41", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k41.save(failOnError: true, flush:true)

                    t2k99 = new PhysicalServer(cluster:solarisCluster, itsId: "t2k99", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    t2k99.save(failOnError: true, flush:true)

                    pub05 = new PhysicalServer(cluster:solarisCluster, itsId: "pub05", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    pub05.save(failOnError: true, flush:true)

                    pub02 = new PhysicalServer(cluster:solarisCluster, itsId: "pub02", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    pub02.save(failOnError: true, flush:true)

                    pub03 = new PhysicalServer(cluster:solarisCluster, itsId: "pub03", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    pub03.save(failOnError: true, flush:true)

                    pvt02 = new PhysicalServer(cluster:solarisCluster, itsId: "pvt02", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    pvt02.save(failOnError: true, flush:true)

                    aphrodite = new PhysicalServer(cluster:standalone, itsId: "aphrodite", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    aphrodite.save(failOnError: true, flush:true)

                    apx01 = new PhysicalServer(cluster:standalone, itsId: "apx01", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    apx01.save(failOnError: true, flush:true)

                    esx56 = new PhysicalServer(cluster:standalone, itsId: "esx56", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    esx56.save(failOnError: true, flush:true)
                    
                    esx56 = new PhysicalServer(cluster:standalone, itsId: "esx59", updatedById: 001, location: locale2, assetType: server, manufacturer: madeBy1, rackable:true, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                    esx56.save(failOnError: true, flush:true)

                }

                def testAsset
                testAsset = new Asset(itsId: "NotServer", location: locale2, assetType: misc, manufacturer: madeBy1, rack:rack1, RU_begin: 0, RU_size:0, RU_planned_begin: 0)
                testAsset.save(failOnError: true, flush:true)


                def replacement1, replacement2, replacement3
                if ( ! Replacement.count() ) {
                    replacement1 = new Replacement( main_asset: t5k99, replacement: t2k40, priority: "1", ready_date: Date.parse('yy-mm-dd', "11-23-2014"))
                    replacement1.save(failOnError: true, flush: true)
                    replacement2 = new Replacement(main_asset: t5k99, replacement: t2k41, priority: "4")
                    replacement2.save(failOnError: true, flush: true)
                    replacement3 = new Replacement(main_asset: t2k40, replacement: t2k41, priority: "2")
                    replacement3.save(failOnError: true, flush: true)
                }

                def aphrodite_mem, aphrodite_cpu, apx01_mem, apx01_cpu, t5k99_mem, t5k99_cpu, t2k40_mem, t2k40_cpu
                def t2k41_cpu, t2k41_mem, t2k99_cpu, t2k99_mem, pub05_cpu, pub05_mem, pub02_cpu, pub02_mem, pub03_cpu, pub03_mem
                def pvt02_cpu, pvt02_mem
                if ( ! AssetCapacity.count() ) {
                    aphrodite_mem = new AssetCapacity( asset: aphrodite, currentCapacity: 8, maxExpandableCapacity: 20, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    aphrodite_cpu = new AssetCapacity( asset: aphrodite, currentCapacity: 12, maxExpandableCapacity: 24, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    apx01_mem = new AssetCapacity( asset: apx01, currentCapacity: 8, maxExpandableCapacity: 20, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    apx01_cpu = new AssetCapacity( asset: apx01, currentCapacity: 12, maxExpandableCapacity: 24, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    t5k99_mem = new AssetCapacity( asset: t5k99, currentCapacity: 40, maxExpandableCapacity: 80, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    t5k99_cpu = new AssetCapacity( asset: t5k99, currentCapacity: 30, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    t2k40_mem = new AssetCapacity( asset: t2k40, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    t2k40_cpu = new AssetCapacity( asset: t2k40, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    t2k41_mem = new AssetCapacity( asset: t2k41, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    t2k41_cpu = new AssetCapacity( asset: t2k41, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    t2k99_mem = new AssetCapacity( asset: t2k99, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    t2k99_cpu = new AssetCapacity( asset: t2k99, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    pub05_mem = new AssetCapacity( asset: pub05, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    pub05_cpu = new AssetCapacity( asset: pub05, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    pub02_mem = new AssetCapacity( asset: pub02, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    pub02_cpu = new AssetCapacity( asset: pub02, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    pub03_mem = new AssetCapacity( asset: pub03, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    pub03_cpu = new AssetCapacity( asset: pub03, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)
                    pvt02_mem = new AssetCapacity( asset: pvt02, currentCapacity: 16, maxExpandableCapacity: 40, resourceType: 'Memory', unitType: 'GB').save(failOnError: true, flush: true)
                    pvt02_cpu = new AssetCapacity( asset: pvt02, currentCapacity: 24, maxExpandableCapacity: 36, resourceType: 'CPU', unitType: 'Threads').save(failOnError: true, flush: true)


                }

                def aphroditeServer, apx01Server, app72, its99, max98,max99, mdb74, odb71, odb72, odb95, ldp36, ldp37, ldp38
                def odb01, frm32, frm33, ssa32, ssa33, odb06, zbx01, dcm01

                if (!Host.count()) {
                    dcm01 = new Host(
                            hostname:'dcm01',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: pvt08,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    dcm01.save(failOnError: true, flush: true)
                    zbx01 = new Host(
                            hostname:'zbx01',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: pvt08,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    zbx01.save(failOnError: true, flush: true)

                    odb01 = new Host(
                            hostname:'odb01',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: pvt08,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    odb01.save(failOnError: true, flush: true)
                    frm32 = new Host(
                            hostname:'frm32',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: VMCluster2,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    frm32.save(failOnError: true, flush: true)
                    frm33 = new Host(
                            hostname:'frm33',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: VMCluster2,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    frm33.save(failOnError: true, flush: true)
                    ssa32 = new Host(
                            hostname:'ssa32',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: VMCluster2,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    ssa32.save(failOnError: true, flush: true)
                    ssa33 = new Host(
                            hostname:'ssa33',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: VMCluster2,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    ssa33.save(failOnError: true, flush: true)
                    odb06 = new Host(
                            hostname:'odb06',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: VMCluster2,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    odb06.save(failOnError: true, flush: true)

                    ldp36 = new Host(
                            hostname:'ldp36',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: t2k06,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    ldp36.save(failOnError: true, flush: true)
                    ldp37 = new Host(
                            hostname:'ldp37',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: t2k07,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    ldp37.save(failOnError: true, flush: true)
                    ldp38 = new Host(
                            hostname:'ldp38',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: t2k08,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    ldp38.save(failOnError: true, flush: true)
                    aphroditeServer = new Host(
                            hostname:'aphrodite',
                            nwaccScan: true,
                            updatedById: 1,
                            solarisFssShare: 0,
                            env: prod,
                            asset: aphrodite,
                            status: availStatus,
                            type: 'Standalone'
                    )
                    aphroditeServer.save(failOnError: true, flush: true)

                    apx01Server = new Host(
                            hostname:'apx01',
                            nwaccScan: false,
                            updatedById: 1,
                            solarisFssShare: 1,
                            env: prod,
                            asset: apx01,
                            status: availStatus,
                            type: 'Standalone'
                    )
                    apx01Server.save(failOnError: true, flush: true)

                    app72 = new Host(
                            hostname:'app72',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    app72.save(failOnError: true, flush: true)

                    its99 = new Host(
                            hostname:'its99',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    its99.save(failOnError: true, flush: true)

                    max98 = new Host(
                            hostname:'max98',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    max98.save(failOnError: true, flush: true)
                    max99 = new Host(
                            hostname:'max99',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    max99.save(failOnError: true, flush: true)
                    mdb74 = new Host(
                            hostname:'mdb74',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'VMWare'
                    )
                    mdb74.save(failOnError: true, flush: true)

                    odb72 = new Host(
                            hostname:'odb72',
                            nwaccScan: false,
                            updatedById: 1,
                            env: test,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    odb72.save(failOnError: true, flush: true)
                    odb71 = new Host(
                            hostname:'odb71',
                            nwaccScan: false,
                            updatedById: 1,
                            env: dev,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    odb71.save(failOnError: true, flush: true)
                    odb95 = new Host(
                            hostname:'odb95',
                            nwaccScan: false,
                            updatedById: 1,
                            env: train,
                            asset: t5k99,
                            status: availStatus,
                            type: 'Non-Global Zone'
                    )
                    odb95.save(failOnError: true, flush: true)

                }
                    System.out.println("AFTER HOST")
                /*
                def aphrodite_alloc1, aphrodite_alloc2, apx01_alloc1, apx01_alloc2, app72_alloc1, app72_alloc2, its99_alloc1, its99_alloc2, mdb74_alloc1, mdb74_alloc2
                def max98_alloc1,max98_alloc2, max99_alloc1,max99_alloc2, odb71_alloc1, odb71_alloc2, odb72_alloc1, odb72_alloc2, odb95_alloc1, odb95_alloc2
                if (!ResourceAllocation.count()) {
                    aphrodite_alloc1 = new ResourceAllocation(cluster: standalone, host: aphroditeServer, asset: aphrodite, resourceType:'Memory', reservedAmount: 8, allocatedAmount: 0, isFixed:true)
                    aphrodite_alloc1.save(failOnError: true, flush: true)
                    aphrodite_alloc2 = new ResourceAllocation(cluster: standalone, host: aphroditeServer, asset: aphrodite, resourceType:'CPU', reservedAmount: 12, allocatedAmount: 0, isFixed:true)
                    aphrodite_alloc2.save(failOnError: true, flush: true)

                    apx01_alloc1 = new ResourceAllocation(cluster: standalone, host: apx01Server, asset: apx01, resourceType:'Memory', reservedAmount: 8, allocatedAmount: 0, isFixed:true)
                    apx01_alloc1.save(failOnError: true, flush: true)
                    apx01_alloc2 = new ResourceAllocation(cluster: standalone, host: apx01Server, asset: apx01, resourceType:'CPU', reservedAmount: 12, allocatedAmount: 0, isFixed:true)
                    apx01_alloc2.save(failOnError: true, flush: true)

                    app72_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: app72, asset: t5k99, resourceType:'Memory', reservedAmount: 2, allocatedAmount: 4, isFixed:true)
                    app72_alloc1.save(failOnError: true, flush: true)
                    app72_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: app72, asset: t5k99, resourceType:'CPU', reservedAmount: 0, allocatedAmount: 2, isFixed:true)
                    app72_alloc2.save(failOnError: true, flush: true)

                    its99_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: its99, asset: t5k99, resourceType:'Memory', reservedAmount: 0, allocatedAmount: 3, isFixed:true)
                    its99_alloc1.save(failOnError: true, flush: true)
                    its99_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: its99, asset: t5k99, resourceType:'CPU', reservedAmount: 0, allocatedAmount: 2, isFixed:true)
                    its99_alloc2.save(failOnError: true, flush: true)

                    mdb74_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: mdb74, asset: t5k99, resourceType:'Memory', reservedAmount: 2, allocatedAmount: 8, isFixed:true)
                    mdb74_alloc1.save(failOnError: true, flush: true)
                    mdb74_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: mdb74, asset: t5k99, resourceType:'CPU', reservedAmount: 1, allocatedAmount: 4, isFixed:true)
                    mdb74_alloc2.save(failOnError: true, flush: true)

                    max98_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: max98, asset: t5k99, resourceType:'Memory', reservedAmount: 1, allocatedAmount: 4, isFixed:true)
                    max98_alloc1.save(failOnError: true, flush: true)
                    max98_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: max98, asset: t5k99, resourceType:'CPU', reservedAmount: 1, allocatedAmount: 4, isFixed:true)
                    max98_alloc2.save(failOnError: true, flush: true)

                    max99_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: max99, asset: t5k99, resourceType:'Memory', reservedAmount: 1, allocatedAmount: 6, isFixed:true)
                    max99_alloc1.save(failOnError: true, flush: true)
                    max99_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: max99, asset: t5k99, resourceType:'CPU', reservedAmount: 0, allocatedAmount: 4, isFixed:true)
                    max99_alloc2.save(failOnError: true, flush: true)

                    odb71_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: odb71, asset: t5k99, resourceType:'Memory', reservedAmount: 2, allocatedAmount: 8, isFixed:true)
                    odb71_alloc1.save(failOnError: true, flush: true)
                    odb71_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: odb71, asset: t5k99, resourceType:'CPU', reservedAmount: 0, allocatedAmount: 8, isFixed:true)
                    odb71_alloc2.save(failOnError: true, flush: true)

                    odb72_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: odb72, asset: t5k99, resourceType:'Memory', reservedAmount: 0, allocatedAmount: 4, isFixed:true)
                    odb72_alloc1.save(failOnError: true, flush: true)
                    odb72_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: odb72, asset: t5k99, resourceType:'CPU', reservedAmount: 1, allocatedAmount: 4, isFixed:true)
                    odb72_alloc2.save(failOnError: true, flush: true)

                    odb95_alloc1 = new ResourceAllocation(cluster: solarisCluster, host: odb95, asset: t5k99, resourceType:'Memory', reservedAmount: 1, allocatedAmount: 8, isFixed:true)
                    odb95_alloc1.save(failOnError: true, flush: true)
                    odb95_alloc2 = new ResourceAllocation(cluster: solarisCluster, host: odb95, asset: t5k99, resourceType:'CPU', reservedAmount: 2, allocatedAmount: 4, isFixed:true)
                    odb95_alloc2.save(failOnError: true, flush: true)

                }
*/
                def appKFS, appLDAP
                def appPS_8, appPS_Prod, appPS_Dev, appPS_Test
                def appCAS, appCASTest
                def BannerUH, BannerUHDR2, BannerPPRDOFM, BannerSEEDMV, BannerSEEDOFM, BannerTRNGOFM, BannerDEVOFM
//                def appNetBackUp, appCheckpointFirewall, appContinuousIntegration, appSolaris10Compile, appCheckpointMGMT
//                def appCognos, appCognos_Test_DB
//                def appExchange2010Test, appMS_SQL, appOracle_Ent_Manager
//                def appDBMonitoring, appDNS, appAnitVirusUpdate, appArchitectureProj, appEnterpriseServiceBus
//                def appVMwareCluster2, appEthority, appFoundryLoadBalancer, appBannerForms, appTestBannerForms
//                def appGmailMigration, appGmailMigration_Test, appIAM_GrailsProject, appGrouper
//                def appHGDR_Resource_Pool, appHitachi_CLI_MGMT_Station, appHKG_Web, appIAM_Webapps, appIdentityFinder
//                def appInfoEd, appIBM_Disk_MGMT, appUHIMS, appInfoSecTeamSearch, appMIS_Prod_Java, appMIS_Prod_Webapps, appMIS_Test_Java
//                def appITS_MIS, appUC4_Agent, appUC4_Server, appSolaris_Software_Repo, appKualiCoeus, appKMM
//                def appContractServices_Kokua, app389_ds_LDAP, appITS_Appliance, appLDAP_Loadtesting
//                def appLoadRunner_Analyzer, appLoadRunner_Controller, appLoadRunner_Generator


                if (! Application.count()) {
                    BannerUH = new Application(applicationTitle: "Banner UH", env:prod, applicationDescription: "Banner UH Production", status:availStatus)
                    BannerUH.save(flush:true, failOnError:true)
                    BannerUHDR2 = new Application(applicationTitle: "Banner UHDR2", env:test, applicationDescription: "Banner UH Load Test", status:availStatus)
                    BannerUHDR2.save(flush:true, failOnError:true)
                    BannerPPRDOFM = new Application(applicationTitle: "Banner PRDOFM", env:pprod, applicationDescription: "Banner Pre-Production OFM", status:availStatus)
                    BannerPPRDOFM.save(flush:true, failOnError:true)
                    BannerDEVOFM = new Application(applicationTitle: "Banner DEVOFM", env:dev, applicationDescription: "Banner UH Development OFM", status:availStatus)
                    BannerDEVOFM.save(flush:true, failOnError:true)
                    BannerSEEDMV = new Application(applicationTitle: "Banner SEEDMV", env:seed, applicationDescription: "Banner Seed MV", status:availStatus, generalNote: "Only used for ODS Loads")
                    BannerSEEDMV.save(flush:true, failOnError:true)
                    BannerSEEDOFM = new Application(applicationTitle: "Banner SEEDOFM", env:seed, applicationDescription: "Banner Seed OFM", status:availStatus)
                    BannerSEEDOFM.save(flush:true, failOnError:true)
                    BannerTRNGOFM = new Application(applicationTitle: "Banner TRNGOFM", env:train, applicationDescription: "Banner Training OFM", status:availStatus)
                    BannerTRNGOFM.save(flush:true, failOnError:true)

                    appKFS = new Application(applicationTitle: "KFS", host: aphroditeServer, env:prod, applicationDescription:"Kuali Financial System", status:availStatus)
                    appKFS.save( flush: true, failOnError: true )

                    appLDAP = new Application(applicationTitle:"LDAP", host:app72, env:prod, status:availStatus)
                    appLDAP.save( flush: true, failOnError: true )

                    ///////
                    appPS_8 = new Application(applicationTitle:"PS_8", host:apx01Server, env:test, applicationDescription: "", status:maintStatus)
                    appPS_8.save( flush: true, failOnError: true)

                    appPS_Prod = new Application(applicationTitle:"PS_Prod", host:its99, env:prod, applicationDescription: "PS Production Application Host", status:availStatus)
                    appPS_Prod.save( flush: true, failOnError: true)

                    appPS_Dev = new Application(applicationTitle:"PS_Dev", host:max98, env:prod, applicationDescription: "PS Development Application Host", status:availStatus)
                    appPS_Dev.save( flush: true, failOnError: true)

                    appPS_Test = new Application(applicationTitle: "PS_Test", host:max99, env:test, applicationDescription: "PS Test application host", status:availStatus)
                    appPS_Test.save( flush: true, failOnError: true )

                    /////////
                    appCAS = new Application(applicationTitle:"CAS", applicationDescription: "CAS Host", env:dev, status:availStatus)
                    appCAS.save( flush: true, failOnError: true )

                    appCASTest = new Application(applicationTitle:"CASTest", env:test, applicationDescription: "CAS test server", status:availStatus)
                    appCASTest.save( flush: true, failOnError: true)
                    ///////////
/*
                    appNetBackUp = new Application(applicationTitle:"NetBackUp_6.5.4", applicationDescription: "", applicationStatus:"Available")
                    appNetBackUp.save( flush: true, failOnError: true)

                    appCheckpointFirewall = new Application(applicationTitle:"CheckpointFirewall", applicationDescription: "", applicationStatus:"Disabled")
                    appCheckpointFirewall.save( flush: true, failOnError: true)

                    appContinuousIntegration = new Application(applicationTitle:"ContinuousIntegration", applicationDescription: "", applicationStatus:"Available")
                    appContinuousIntegration.save( flush: true, failOnError: true)

                    appSolaris10Compile = new Application(applicationTitle:"Solaris10Compile", applicationDescription: "", applicationStatus:"Maintenance")
                    appSolaris10Compile.save( flush: true, failOnError: true)

                    appCheckpointMGMT = new Application(applicationTitle:"CheckpointMGMT", applicationDescription: "", applicationStatus:"Available")
                    appCheckpointMGMT.save( flush: true, failOnError: true)

                    //////
                    appCognos = new Application(applicationTitle:"Cognos", applicationDescription: "Cognos application Host", applicationStatus:"Available")
                    appCognos.save( flush: true, failOnError: true)

                    appCognos_Test_DB = new Application(applicationTitle:"Cognos_Test_DB", applicationDescription: "Cognos Test Database Host", applicationStatus:"Maintenance")
                    appCognos_Test_DB.save( flush: true, failOnError: true)

                    /////////
                    appExchange2010Test = new Application(applicationTitle:"Exchange2010Test", applicationDescription: "Exchange 2010 Test Host", applicationStatus:"Available")
                    appExchange2010Test.save( flush: true, failOnError: true)

                    appMS_SQL= new Application(applicationTitle:"MS_SQL", applicationDescription: "MS SQL", applicationStatus:"Available")
                    appMS_SQL.save( flush: true, failOnError: true)

                    //////////
                    appOracle_Ent_Manager = new Application(applicationTitle:"Oracle_Ent_Manager", applicationDescription: "Oracle Enterprise Manager", applicationStatus:"Maintenance")
                    appOracle_Ent_Manager.save( flush: true, failOnError: true)

                    appDBMonitoring = new Application(applicationTitle:"DBMonitoring", applicationDescription: "Database Monitoring", applicationStatus:"Disabled")
                    appDBMonitoring.save( flush: true, failOnError: true)

                    appDNS = new Application(applicationTitle:"DNS", status:"Available")
                    appDNS.save( flush: true, failOnError: true)

                    appAnitVirusUpdate = new Application(applicationTitle:"AnitVirusUpdate", applicationDescription: "AntiVirus Update Host", applicationStatus:"Available")
                    appAnitVirusUpdate.save( flush: true, failOnError: true)

                    appArchitectureProj = new Application(applicationTitle:"ArchitectureProject", applicationDescription: "Architecture Project", applicationStatus:"Maintenance")
                    appArchitectureProj.save( flush: true, failOnError: true)

                    appEnterpriseServiceBus = new Application(applicationTitle:"EnterpriseServiceBus", applicationDescription: "Enterprise Service Bus", applicationStatus:"Available")
                    appEnterpriseServiceBus.save( flush: true, failOnError: true)

                    ///////////

                    appVMwareCluster2 = new Application(applicationTitle:"VMwareCluster2", applicationDescription: "VMware Cluster 2", applicationStatus:"Available")
                    appVMwareCluster2.save( flush: true, failOnError: true)

                    appEthority = new Application(applicationTitle:"Ethority", applicationDescription: "eThority Web Host", applicationStatus:"Available")
                    appEthority.save( flush: true, failOnError: true)

                    appFoundryLoadBalancer = new Application(applicationTitle:"FoundryLoadBalancer", applicationDescription: "Foundry Load Balancer", applicationStatus:"Maintenance")
                    appFoundryLoadBalancer.save( flush: true, failOnError: true)

                    appBannerForms = new Application(applicationTitle:"BannerForms", applicationDescription: "Banner Forms Host", applicationStatus:"Available")
                    appBannerForms.save( flush: true, failOnError: true)

                    appTestBannerForms = new Application(applicationTitle:"TestBannerForms", applicationDescription: "Test Banner Forms Host", applicationStatus:"Disabled")
                    appTestBannerForms.save( flush: true, failOnError: true)
                    ///////////////
                    appGmailMigration = new Application(applicationTitle:"GmailMigration", applicationDescription: "Gmail Migration", applicationStatus:"Maintenance")
                    appGmailMigration.save( flush: true, failOnError: true)

                    appGmailMigration_Test = new Application(applicationTitle:"GmailMigration_Test", applicationDescription: "Gmail Migration Host", applicationStatus:"Available")
                    appGmailMigration_Test.save( flush: true, failOnError: true)

                    appIAM_GrailsProject = new Application(applicationTitle:"IAM_GrailsProject", applicationDescription: "IAM Grails Project", applicationStatus:"Available")
                    appIAM_GrailsProject.save( flush: true, failOnError: true)

                    appGrouper = new Application(applicationTitle:"Grouper_app", applicationDescription: "Grouper App Host", applicationStatus:"Available")
                    appGrouper.save( flush: true, failOnError: true)

                    appHGDR_Resource_Pool = new Application(applicationTitle:"HGDR_Resource_Pool", applicationDescription: "HGDR Resource Pool", applicationStatus:"Maintenance")
                    appHGDR_Resource_Pool.save( flush: true, failOnError: true)

                    appHitachi_CLI_MGMT_Station = new Application(applicationTitle:"Hitachi_CLI_MGMT_Station", applicationDescription: "Hitachi Navigator CLI Management Station", applicationStatus:"Disabled")
                    appHitachi_CLI_MGMT_Station.save( flush: true, failOnError: true)

                    appHKG_Web = new Application(applicationTitle:"HKG_Web", applicationDescription: "HKG Web server", applicationStatus:"Available")
                    appHKG_Web.save( flush: true, failOnError: true)

                    appIAM_Webapps = new Application(applicationTitle:"IAM_Webapps", applicationDescription: "IAM Webapps UHIMC and WPMS", applicationStatus:"Maintenance")
                    appIAM_Webapps.save( flush: true, failOnError: true)

                    appIdentityFinder = new Application(applicationTitle:"IdentityFinder", applicationDescription: "Identity Finder", applicationStatus:"Available")
                    appIdentityFinder.save( flush: true, failOnError: true)

                    ////////////
                    appInfoEd = new Application(applicationTitle:"InfoEd", applicationDescription: "InfoEd Application Host", applicationStatus:"Available")
                    appInfoEd.save( flush: true, failOnError: true)

                    appIBM_Disk_MGMT = new Application(applicationTitle:"IBM_Disk_MGMT", applicationDescription: "IBM Disk Management Host", applicationStatus:"Available")
                    appIBM_Disk_MGMT.save( flush: true, failOnError: true)

                    appUHIMS = new Application(applicationTitle:"UHIMS", applicationDescription: "UHIMS Application Host", applicationStatus:"Disabled")
                    appUHIMS.save( flush: true, failOnError: true)

                    appInfoSecTeamSearch = new Application(applicationTitle:"InfoSecTeamSearch", applicationDescription: "Info Sec Team Search Host", applicationStatus:"Available")
                    appInfoSecTeamSearch.save( flush: true, failOnError: true)

                    appMIS_Prod_Java = new Application(applicationTitle:"MIS_Prod_Java", applicationDescription: "MIS production java web server", applicationStatus:"Maintenance")
                    appMIS_Prod_Java.save( flush: true, failOnError: true)

                    appMIS_Prod_Webapps = new Application(applicationTitle:"MIS_Prod_Webapps", applicationDescription: "MIS Production Webapps", applicationStatus:"Available")
                    appMIS_Prod_Webapps.save( flush: true, failOnError: true)

                    appMIS_Test_Java = new Application(applicationTitle:"MIS_Test_Java", applicationDescription: "MIS test java webapps", applicationStatus:"Available")
                    appMIS_Test_Java.save( flush: true, failOnError: true)

                    //////////
                    appITS_MIS = new Application(applicationTitle:"ITS_MIS", applicationDescription: "ITS MIS application server", applicationStatus:"Disabled")
                    appITS_MIS.save( flush: true, failOnError: true)

                    appUC4_Agent = new Application(applicationTitle:"UC4_Agent", applicationDescription: "", applicationStatus:"Available")
                    appUC4_Agent.save( flush: true, failOnError: true)

                    appUC4_Server = new Application(applicationTitle:"UC4_Server", applicationDescription: "", applicationStatus:"Maintenance")
                    appUC4_Server.save( flush: true, failOnError: true)

                    appSolaris_Software_Repo = new Application(applicationTitle:"Solaris_Software_Repo", applicationDescription: "Solaris software repository", applicationStatus:"Available")
                    appSolaris_Software_Repo.save( flush: true, failOnError: true)

                    appKualiCoeus = new Application(applicationTitle:"KualiCoeus", applicationDescription: "kuali Coeus applicationhost", applicationStatus:"Available")
                    appKualiCoeus.save( flush: true, failOnError: true)

                    appKMM = new Application(applicationTitle:"KMM", applicationDescription: "KMM application host", applicationStatus:"Disabled")
                    appKMM.save( flush: true, failOnError: true)

                    ///////////
                    appContractServices_Kokua = new Application(applicationTitle:"ContractServices_Kokua", applicationDescription: "Contract Service: Kokua Host", applicationStatus:"Maintenance")
                    appContractServices_Kokua.save( flush: true, failOnError: true)

                    app389_ds_LDAP = new Application(applicationTitle:"389_ds_LDAP",applicationDescription: "398 ds LDAP Host",  applicationStatus:"Available")
                    app389_ds_LDAP.save( flush: true, failOnError: true)

                    appITS_Appliance = new Application(applicationTitle:"ITS_Appliance", applicationDescription: "ITS Appliance", applicationStatus:"Available")
                    appITS_Appliance.save( flush: true, failOnError: true)

                    appLDAP_Loadtesting = new Application(applicationTitle:"LDAP_Loadtesting", applicationDescription: "LDAP Load Testing", applicationStatus:"Maintenance")
                    appLDAP_Loadtesting.save( flush: true, failOnError: true)

                    //////////////
                    appLoadRunner_Analyzer = new Application(applicationTitle:"LoadRunner_Analyzer", applicationDescription: "LoadRunner Analyzer", applicationStatus:"Maintenance")
                    appLoadRunner_Analyzer.save( flush: true, failOnError: true)

                    appLoadRunner_Controller = new Application(applicationTitle:"appLoadRunner_Controller", applicationDescription: "LoadRunner Controller", applicationStatus:"Maintenance")
                    appLoadRunner_Controller.save( flush: true, failOnError: true)

                    appLoadRunner_Generator = new Application(applicationTitle:"appLoadRunner_Generator", applicationDescription: "LoadRunner Generator", applicationStatus:"Available")
                    appLoadRunner_Generator.save( flush: true, failOnError: true)

*/
                }

                def serviceLDAP1, serviceLDAP2, serviceLDAP3, serviceLDAP4
                def BannerDBB, BannerForms, BannerSS, BannerFA, AssocODS
                if(!Service.count()) {
                    serviceLDAP1 = new Service(serviceTitle:'Sun LDAP', application: appLDAP, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    serviceLDAP2 = new Service(serviceTitle:'Sun LDAP Anonymous Queries', env:prod, status:availStatus, application: appLDAP).save(failOnError: true, flush: true)
                    serviceLDAP3 = new Service(serviceTitle:'389ds ldapmaster', application: appLDAP, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    serviceLDAP4 = new Service(serviceTitle:'389ds ldap.hawaii.edu', application: appLDAP, env:prod, status:availStatus).save(failOnError: true, flush: true)

                    BannerDBB = new Service(serviceTitle:'Banner Database and Batch', application: BannerUH, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    BannerForms = new Service(serviceTitle:'Banner Forms', application: BannerUH, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    BannerSS = new Service(serviceTitle:'Banner Self Service', application: BannerUH, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    BannerFA = new Service(serviceTitle:'Banner Financial Aid', application: BannerUH, env:prod, status:availStatus).save(failOnError: true, flush: true)
                    AssocODS = new Service(serviceTitle:'Banner Associated ODS', application: BannerUH, env:prod, status:availStatus).save(failOnError: true, flush: true)



                }


                def tierWebLDAP1, tierWebLDAP2, tierWebLDAP3, tierDBLDAP
                def BannerDBB1, BannerForms1, BannerForms2, BannerSS1, BannerSS2, AssocODS1
                if(! Tier.count()) {
                    tierWebLDAP1 = new Tier(tierName: 'Sun LDAP Instance 1', loadBalanced: true, mainApp: appLDAP, host: ldp36).save(failOnError: true, flush: true)
                    tierWebLDAP2 = new Tier(tierName: 'Sun LDAP Instance 2', loadBalanced: true, mainApp: appLDAP, host: ldp37).save(failOnError: true, flush: true)
                    tierWebLDAP3 = new Tier(tierName: 'Sun LDAP Instance 3', loadBalanced: true, mainApp: appLDAP, host: ldp38).save(failOnError: true, flush: true)
                    tierDBLDAP = new Tier(tierName: 'LDAP DB', loadBalanced: false, mainApp: appLDAP, host: odb71).save(failOnError: true, flush: true)

                    BannerDBB1 = new Tier(tierName: 'Banner DB/Batch Instance 1', loadBalanced: false, mainApp: BannerUH, host: odb01).save(failOnError: true, flush: true)
                    BannerForms1 = new Tier(tierName: 'Banner Forms Instance 1', loadBalanced: false, mainApp: BannerUH, host: frm32).save(failOnError: true, flush: true)
                    BannerForms2 = new Tier(tierName: 'Banner Forms Instance 2', loadBalanced: false, mainApp: BannerUH, host: frm33).save(failOnError: true, flush: true)
                    BannerSS1 = new Tier(tierName: 'Banner Self-Service Instance 1', loadBalanced: false, mainApp: BannerUH, host: ssa32).save(failOnError: true, flush: true)
                    BannerSS2 = new Tier(tierName: 'Banner Self-Service Instance 2', loadBalanced: false, mainApp: BannerUH, host: ssa33).save(failOnError: true, flush: true)
                    AssocODS1 = new Tier(tierName: 'Banner ODS 1', loadBalanced: false, mainApp: BannerUH, host: odb06).save(failOnError: true, flush: true)



                }


                def serviceHasTierLDAP1, serviceHasTierLDAP2, serviceHasTierLDAP3, serviceHasTierLDAP4, serviceHasTierLDAP5
                def BannerDBBDep1, BannerFormDep1, BannerFormDep2, BannerSSDep1, BannerSSDep2, AssocODSDep1
                if(!TierDependency.count()) {
                    serviceHasTierLDAP1 = new TierDependency(service: serviceLDAP1, tier: tierWebLDAP1, serviceInstance: true).save(failOnError: true, flush: true)
                    serviceHasTierLDAP2 = new TierDependency(service: serviceLDAP1, tier: tierWebLDAP2, serviceInstance: true).save(failOnError: true, flush: true)
                    serviceHasTierLDAP3 = new TierDependency(service: serviceLDAP1, tier: tierWebLDAP3, serviceInstance: true).save(failOnError: true, flush: true)
                    serviceHasTierLDAP4 = new TierDependency(service: serviceLDAP2, tier: tierWebLDAP2, serviceInstance: true).save(failOnError: true, flush: true)
                    serviceHasTierLDAP4 = new TierDependency(service: serviceLDAP1, tier: tierDBLDAP, serviceInstance: false).save(failOnError: true, flush: true)

                    BannerDBBDep1 = new TierDependency(service: BannerDBB, tier: BannerDBB1, serviceInstance: true).save(failOnError: true, flush: true)
                    BannerFormDep1 = new TierDependency(service: BannerForms, tier: BannerForms1, serviceInstance: true).save(failOnError: true, flush: true)
                    BannerFormDep2 = new TierDependency(service: BannerForms, tier: BannerForms2, serviceInstance: true).save(failOnError: true, flush: true)
                    BannerSSDep1 = new TierDependency(service: BannerSS, tier: BannerSS1, serviceInstance: true).save(failOnError: true, flush: true)
                    BannerSSDep2 = new TierDependency(service: BannerSS, tier: BannerSS2, serviceInstance: true).save(failOnError: true, flush: true)
                    AssocODSDep1 = new TierDependency(service: AssocODS, tier: AssocODS1, serviceInstance: true).save(failOnError: true, flush: true)


                }

//                def appDependencyVMwareLDAP
//

                if ( ! ServiceLevelAgreement.count() ) {
                    def sla1 = new ServiceLevelAgreement( slaTitle: "Superior Service", slaType: "Enterprise" )
                    sla1.save(failOnError: true, flush: true)
                }

                def normalTierType
                if (!TierType.count()) {
                    normalTierType = new TierType(abbreviation: 'Normal').save(failOnError:true, flush:true)
                }

               def person1, person2, jasonDomanay, manoFaria, vernonYoshida, hawOkimoto, susanInouye, steveSmith, melissaTome, michaelImai
                def mitchellOchi, michaelHodges, kenwrickChan, mitchellAnicas, garryAu, stevenSakata, osamuMakiguchi, caraKawano, tammyVandevender
                def charlesAoki, cameronAhana
               // if (!Person.count() && !ContactInfo.count()) {
                    if (!Person.count()) {

                        person1 = new Person(uhName: "obama", uhNumber:"11111111", title:"President", lastName:"Obama", firstName:"Barrack", midInit:"H")

                 //   person1.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-444-4444", isPrimary:true)
                  //  person1.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "me@example.com", isPrimary:true)
                   // person1.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1111", isPrimary:false)
                   // person1.addToContactInfos new ContactInfo(contactType: "Fax", contactInfo: "808-222-2222", isPrimary:false)
                    person1.save(failOnError: true, flush: true)

                    person2 = new Person(uhName:"clinton", uhNumber:"22222222", title:"Construction Engineer", lastName:"Flintsone", firstName:"Fred", midInit:"").save(faileOnError: true)
                    def person3 = new Person(uhNumber:"33333333", title:"Secretary of State", lastName:"Clinton", firstName:"Hillary", midInit:"R").save(faileOnError: true)
                    def person4 = new Person(uhNumber:"44444444", title:"System Administrator", lastName:"Rubble", firstName:"Betty", midInit:"").save(faileOnError: true, flush: true)

                    jasonDomanay = new Person(uhName:"domanay", uhNumber:"10000001", title:"Database Administrator", lastName:"Domanay", firstName:"Jason")
                   // jasonDomanay.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1111", isPrimary:true)
                   // jasonDomanay.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "jasondomanay@hawaii.edu", isPrimary:true)
                    jasonDomanay.save(failOnError: true, flush: true)

                    manoFaria= new Person(uhName:"mfaria", uhNumber:"10000002", title:"Database Administrator", lastName:"Faria", firstName:"Mano")
                   // manoFaria.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1112", isPrimary:true)
                   // manoFaria.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "manoFaria@hawaii.edu", isPrimary:true)
                    manoFaria.save(failOnError: true, flush: true)
                    vernonYoshida = new Person(uhName:"vernon", uhNumber:"10000003", title:"Database Administrator", lastName:"Yoshida", firstName:"Vernon")
                   // vernonYoshida.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1113", isPrimary:true)
                   // vernonYoshida.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "vernonYoshida@hawaii.edu", isPrimary:true)
                    vernonYoshida.save(failOnError: true, flush: true)
                    hawOkimoto= new Person(uhName:"okimoto", uhNumber:"10000004", title:"", lastName:"Okimoto", firstName:"Hae")
                   // hawOkimoto.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1114", isPrimary:true)
                   // hawOkimoto.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "hawOkimoto@hawaii.edu", isPrimary:true)
                    hawOkimoto.save(failOnError: true, flush: true)
                    susanInouye= new Person(uhName:"inouye", uhNumber:"10000005", title:"", lastName:"Inouye", firstName:"Susan")
                   // susanInouye.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1115", isPrimary:true)
                   // susanInouye.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "susanInouye@hawaii.edu", isPrimary:true)
                    susanInouye.save(failOnError: true, flush: true)
                    steveSmith= new Person(uhName:"stsmith", uhNumber:"10000006", title:"", lastName:"Smith", firstName:"Steve")
                   // steveSmith.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1116", isPrimary:true)
                   // steveSmith.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "steveSmith@hawaii.edu", isPrimary:true)
                    steveSmith.save(failOnError: true, flush: true)
                    melissaTome= new Person(uhName:"mtome", uhNumber:"10000007", title:"", lastName:"Tome", firstName:"Melissa")
                   // melissaTome.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1117", isPrimary:true)
                   // melissaTome.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "melissaTome@hawaii.edu", isPrimary:true)
                    melissaTome.save(failOnError: true, flush: true)
                    michaelImai= new Person(uhName:"mimai", uhNumber:"10000008", title:"", lastName:"Imai", firstName:"Michael")
                  //  michaelImai.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1118", isPrimary:true)
                  //  michaelImai.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "michaelImai@hawaii.edu", isPrimary:true)
                    michaelImai.save(failOnError: true, flush: true)
                    mitchellOchi= new Person(uhName:"mochi", uhNumber:"10000009", title:"", lastName:"Ochi", firstName:"Mitchell")
                //    mitchellOchi.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1119", isPrimary:true)
                //    mitchellOchi.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "mitchellOchi@hawaii.edu", isPrimary:true)
                    mitchellOchi.save(failOnError: true, flush: true)
                    michaelHodges= new Person(uhName:"mhodges", uhNumber:"10000016", title:"", lastName:"Hodges", firstName:"Michael")
                //    michaelHodges.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1110", isPrimary:true)
                //    michaelHodges.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "michaelHodges@hawaii.edu", isPrimary:true)
                    michaelHodges.save(failOnError: true, flush: true)
                    kenwrickChan= new Person(uhName:"kenwrick", uhNumber:"10000017", title:"", lastName:"Chan", firstName:"Kenwrick")
                 //   kenwrickChan.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1127", isPrimary:true)
                  //  kenwrickChan.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "kenwrickChan@hawaii.edu", isPrimary:true)
                    kenwrickChan.save(failOnError: true, flush: true)
                    mitchellAnicas= new Person(uhName:"anicasm", uhNumber:"10000018", title:"System Administrator", lastName:"Anicas", firstName:"Mitchell")
                  //  mitchellAnicas.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1128", isPrimary:true)
                  //  mitchellAnicas.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "mitchellAnicas@hawaii.edu", isPrimary:true)
                    mitchellAnicas.save(failOnError: true, flush: true)
                    garryAu= new Person(uhName:"garry", uhNumber:"10000010", title:"System Administrator", lastName:"Au", firstName:"Garry")
                   // garryAu.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1120", isPrimary:true)
                   // garryAu.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "garryAu@hawaii.edu", isPrimary:true)
                    garryAu.save(failOnError: true, flush: true)
                    stevenSakata= new Person(uhName:"steven", uhNumber:"10000011", title:"", lastName:"Sakata", firstName:"Steven")
                   // stevenSakata.addToContactInfos new ContactInfo(contactType: "Phone", contactInfo: "808-111-1121", isPrimary:true)
                   // stevenSakata.addToContactInfos new ContactInfo(contactType: "Email", contactInfo: "stevenSakata@hawaii.edu", isPrimary:true)
                    stevenSakata.save(failOnError: true, flush: true)
                    osamuMakiguchi= new Person(uhName:"osamu", uhNumber:"10000012", title:"", lastName:"Makiguchi", firstName:"Osamu")
                    osamuMakiguchi.save(failOnError: true, flush: true)
                    caraKawano= new Person(uhName:"kawano", uhNumber:"10000013", title:"", lastName:"Kawano", firstName:"Cara")
                    caraKawano.save(failOnError: true, flush: true)
                    tammyVandevender= new Person(uhName:"tammy", uhNumber:"10000014", title:"", lastName:"Vandevender", firstName:"Tammy")
                    tammyVandevender.save(failOnError: true, flush: true)
                    charlesAoki= new Person(uhName:"aoki",uhNumber:"10000015", title:"", lastName:"Aoki", firstName:"Charles")
                    charlesAoki.save( failOnError: true, flush: true)
                    cameronAhana= new Person(uhName:"ahana", uhNumber:"10000019", title:"", lastName:"Ahana", firstName:"Cameron")
                    cameronAhana.save(failOnError: true, flush: true)
                }

                def rolePrimarySA, roleDBA, roleFuncLead, roleLeadDev,roleLeadTech, roleProjMan, roleSecondSA, roleThirdSA, roleServLead
                def rolePrimaryDBA, roleSecondDBA, roleThirdDBA
                if (!RoleType.count()) {
                    roleDBA = new RoleType(roleName:'Database Administrator').save(failOnError:true)
                    roleFuncLead = new RoleType(roleName:'Functional Lead').save(failOnError:true)
                    roleLeadDev = new RoleType(roleName:'Developer Lead').save(failOnError:true)
                    roleLeadTech = new RoleType(roleName:'Technical Lead').save(failOnError:true)
                    roleProjMan = new RoleType(roleName:'Project Manager').save(failOnError:true)
                    rolePrimarySA = new RoleType(roleName:'Primary SA').save(failOnError:true)
                    roleSecondSA = new RoleType(roleName:'Secondary SA').save(failOnError:true)
                    roleThirdSA = new RoleType(roleName:'Tertiary SA').save(failOnError:true)
                    rolePrimaryDBA = new RoleType(roleName:'Primary DBA').save(failOnError:true)
                    roleSecondDBA = new RoleType(roleName:'Secondary DBA').save(failOnError:true)
                    roleThirdDBA = new RoleType(roleName:'Tertiary DBA').save(failOnError:true)
                    roleServLead = new RoleType(roleName:'Service Lead').save(failOnError:true)

                }

                if(!SupportRole.count()){

                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:stevenSakata, supportedObject:BannerUH).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:mitchellAnicas, supportedObject:BannerDEVOFM).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:mitchellAnicas, supportedObject:BannerPPRDOFM).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:stevenSakata, supportedObject:BannerSEEDMV).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:stevenSakata, supportedObject:BannerSEEDOFM).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:stevenSakata, supportedObject:BannerTRNGOFM).save(failOnError:true)

                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:jasonDomanay, supportedObject: odb95).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:manoFaria, supportedObject: aphroditeServer).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:hawOkimoto, supportedObject: app72).save(failOnError:true)
                    new SupportRole(roleName:roleDBA, roleType:'Technical', person:vernonYoshida, supportedObject: app72).save(failOnError:true)
                    new SupportRole(roleName:roleDBA, roleType:'Technical', person:vernonYoshida, supportedObject: appKFS).save(failOnError:true)
                    new SupportRole(roleName:roleDBA, roleType:'Technical', person:manoFaria, supportedObject: appKFS).save(failOnError:true)

                    /*
                    new SupportRole(roleName:'Executive Lead', roleType:'Functional', person:hawOkimoto, supportedObject: t5k99).save(failOnError:true)
                    new SupportRole(roleName:'Executive Lead', roleType:'Functional', person:susanInouye, supportedObject: app72).save(failOnError:true)
                    new SupportRole(roleName:'Executive Lead', roleType:'Functional', person:michaelImai, supportedObject: appKFS).save(failOnError:true)

                    new SupportRole(roleName:'Functional Lead', roleType:'Functional', person:melissaTome, supportedObject: appKFS).save(failOnError:true)
                    new SupportRole(roleName:'Functional Lead', roleType:'Functional', person:michaelImai, supportedObject: t5k99).save(failOnError:true)

                    new SupportRole(roleName:'Key Stakeholder', roleType:'Functional', person:mitchellOchi, supportedObject: appKFS).save(failOnError:true)
                    new SupportRole(roleName:'Key Stakeholder', roleType:'Functional', person:michaelHodges, supportedObject: t5k99).save(failOnError:true)
                    new SupportRole(roleName:'Key Stakeholder', roleType:'Functional', person:kenwrickChan, supportedObject: t5k99).save(failOnError:true)

                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:mitchellAnicas, supportedObject: app72).save(failOnError:true)
                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:mitchellAnicas, supportedObject: appKFS).save(failOnError:true)
                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:garryAu, supportedObject: t5k99).save(failOnError:true)
                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:stevenSakata, supportedObject: t5k99).save(failOnError:true)
                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:stevenSakata, supportedObject: app72).save(failOnError:true)
                    new SupportRole(roleName:'System Administrator', roleType:'Technical', person:stevenSakata, supportedObject: appKFS).save(failOnError:true)

                    new SupportRole(roleName:'Team Lead', roleType:'Functional', person:osamuMakiguchi, supportedObject:t5k99).save(failOnError:true)
                    new SupportRole(roleName:'Team Lead', roleType:'Functional', person:caraKawano, supportedObject:appKFS).save(failOnError:true)
                    new SupportRole(roleName:'Team Lead', roleType:'Functional', person:tammyVandevender, supportedObject:app72).save(failOnError:true)

                    new SupportRole(roleName:'Techincal Lead', roleType:'Technical', person:charlesAoki, supportedObject:appKFS).save(failOnError:true)
                    new SupportRole(roleName:'Technical Lead', roleType:'Technical', person:kenwrickChan, supportedObject:app72).save(failOnError:true)
                    new SupportRole(roleName:'Technical Lead', roleType:'Technical', person:cameronAhana, supportedObject:t5k99).save(failOnError:true)

*/
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:osamuMakiguchi, supportedObject:appKFS).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:caraKawano, supportedObject:appPS_8).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:tammyVandevender, supportedObject:appPS_Prod).save(failOnError:true)

                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:charlesAoki, supportedObject:appPS_Dev).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:kenwrickChan, supportedObject:appPS_Test).save(failOnError:true)
                    new SupportRole(roleName:rolePrimarySA, roleType:'Technical', person:cameronAhana, supportedObject:appLDAP).save(failOnError:true)

//                    new SupportRole(roleName:'Lead', roleType:'Technical', person:stevenSakata, supportedObject: server1).save(failOnError:true)

//                    new SupportRole(roleName:'owner', roleType:'Functional', person:person2, supportedObject: appKFS).save(failOnError:true)
//                    new SupportRole(roleName:'SA', roleType:'Technical', person:mitchellAnicas, supportedObject: appKFS).save(failOnError:true)
//                    new SupportRole(roleName:'SA', roleType:'Technical', person:mitchellAnicas, supportedObject: appLDAP).save(failOnError:true)
//                    new SupportRole(roleName:'SA', roleType:'Technical', person:mitchellAnicas, supportedObject: appCAS).save(failOnError:true)

//                    new SupportRole(name:'consultant').save(failOnError:true)
//                    new SupportRole(name:'vendor representative').save(failOnError:true)
//                    new SupportRole(name:'vendor contract manager').save(failOnError:true)
//                    new SupportRole(name:'uh contract manager').save(failOnError:true)
                }


                System.out.println("Before PSU")
                def PSU1, PSU2, PSU3, PSU4
                if(!PowerSource.count()) {
                    PSU1 = new PowerSource(itsId:'A-1', capacity:100, assetType:power).save(failOnError:true)
                    PSU2 = new PowerSource(itsId:'B-1', capacity:100, assetType:power).save(failOnError:true)
                    PSU3 = new PowerSource(itsId:'A-2', capacity:100, assetType:power).save(failOnError:true)
                    PSU4 = new PowerSource(itsId:'B-2', capacity:100, assetType:power).save(failOnError:true)
                }

                System.out.println("Before Bus")
                def Bus1, Bus2, Bus3, Bus4, Bus5, Bus6, Bus7, Bus8
                if(!PowerPanel.count()) {
                    Bus1 = new PowerPanel(itsId:'A-1', capacity:50, source:PSU1, assetType:power, breaker_poles:16).save(failOnError:true)
                    Bus2 = new PowerPanel(itsId:'B-1', capacity:50, source:PSU1, assetType:power, breaker_poles:8).save(failOnError:true)
                    Bus3 = new PowerPanel(itsId:'A-2', capacity:50, source:PSU2, assetType:power, breaker_poles:4).save(failOnError:true)
                    Bus4 = new PowerPanel(itsId:'B-2', capacity:50, source:PSU2, assetType:power, breaker_poles:16).save(failOnError:true)
                    Bus5 = new PowerPanel(itsId:'C-1', capacity:50, source:PSU3, assetType:power, breaker_poles:16).save(failOnError:true)
                    Bus6 = new PowerPanel(itsId:'C-2', capacity:50, source:PSU4, assetType:power, breaker_poles:16).save(failOnError:true)
                    Bus7 = new PowerPanel(itsId:'D-1', capacity:50, source:PSU3, assetType:power, breaker_poles:16).save(failOnError:true)
                    Bus8 = new PowerPanel(itsId:'D-2', capacity:50, source:PSU4, assetType:power, breaker_poles:16).save(failOnError:true)
                }

                System.out.println("Before PowerBreaker")
                def A01a, A02a, A03a, A04a, A05a, A06a, A07a, A08a, A09a, A10a, A11a, A12a, A13a, A14a
                def A01b, A02b, A03b, A04b, A05b, A06b, A07b, A08b, A09b, A10b, A11b, A12b, A13b, A14b
                def B01a, B02a, B03a, B04a, B05a, B06a, B07a, B08a, B09a, B10a, B11a, B12a, B13a, B14a
                if(!PowerBreaker.count()) {
                    A01a = new PowerBreaker(itsId:'A01-a', label: 'A01-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A02a = new PowerBreaker(itsId:'A02-a', label: 'A02-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A03a = new PowerBreaker(itsId:'A03-a', label: 'A03-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A04a = new PowerBreaker(itsId:'A04-a', label: 'A04-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A05a = new PowerBreaker(itsId:'A05-a', label: 'A05-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A06a = new PowerBreaker(itsId:'A06-a', label: 'A06-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A07a = new PowerBreaker(itsId:'A07-a', label: 'A07-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A08a = new PowerBreaker(itsId:'A08-a', label: 'A08-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A09a = new PowerBreaker(itsId:'A09-a', label: 'A09-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A10a = new PowerBreaker(itsId:'A10-a', label: 'A10-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A11a = new PowerBreaker(itsId:'A11-a', label: 'A11-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A12a = new PowerBreaker(itsId:'A12-a', label: 'A12-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A13a = new PowerBreaker(itsId:'A13-a', label: 'A13-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)
                    A14a = new PowerBreaker(itsId:'A14-a', label: 'A14-a', capacity:30, panel:Bus1, assetType:power, voltage: 110).save(failOnError:true)

                    A01b = new PowerBreaker(itsId:'A01-b', label: 'A01-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A02b = new PowerBreaker(itsId:'A02-b', label: 'A02-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A03b = new PowerBreaker(itsId:'A03-b', label: 'A03-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A04b = new PowerBreaker(itsId:'A04-b', label: 'A04-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A05b = new PowerBreaker(itsId:'A05-b', label: 'A05-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A06b = new PowerBreaker(itsId:'A06-b', label: 'A06-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A07b = new PowerBreaker(itsId:'A07-b', label: 'A07-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A08b = new PowerBreaker(itsId:'A08-b', label: 'A08-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A09b = new PowerBreaker(itsId:'A09-b', label: 'A09-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A10b = new PowerBreaker(itsId:'A10-b', label: 'A10-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A11b = new PowerBreaker(itsId:'A11-b', label: 'A11-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A12b = new PowerBreaker(itsId:'A12-b', label: 'A12-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A13b = new PowerBreaker(itsId:'A13-b', label: 'A13-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)
                    A14b = new PowerBreaker(itsId:'A14-b', label: 'A14-b', capacity:30, panel:Bus3, assetType:power, voltage: 110).save(failOnError:true)

                }

                def connector1, connector2, connector3
                if (! PowerConnector.count() ) {
                    connector1 = new PowerConnector(name:'C1').save(failOnError: true, flush:true)
                    connector2 = new PowerConnector(name:'C2').save(failOnError: true, flush:true)
                    connector3 = new PowerConnector(name:'C3').save(failOnError: true, flush:true)
                }

                def PSType1,PSType2
                if (! PowerStripType.count()) {
                    PSType1 = new PowerStripType(name:'PSType1', connectors: [connector1, connector1, connector1, connector1, connector2, connector2]).save(failOnError: true, flush:true)
                    PSType2 = new PowerStripType(name:'PSType2', connectors: [connector2, connector3, connector3]).save(failOnError: true, flush:true)
                }


                System.out.println("Before Strip")
                def strip2, strip3, strip4, strip5, strip6, strip7, strip8, strip9, strip10, strip11, strip12, strip13
                if(!PowerStrip.count()) {

                    strip2 = new PowerStrip(itsId:'XY', capacity:10, breaker:A01a, powerUsed:6, assetType:power, type: PSType2).save(failOnError:true)
                    strip3 = new PowerStrip(itsId:'XZ', capacity:10, breaker:A01a, powerUsed:10, assetType:power, type: PSType2).save(failOnError:true)
                    strip4 = new PowerStrip(itsId:'YZ', capacity:10, breaker:A01a, powerUsed:10, assetType:power).save(failOnError:true)
                    strip5 = new PowerStrip(itsId:'XY', capacity:10, breaker:A01b, powerUsed:2, assetType:power).save(failOnError:true)
                    strip6 = new PowerStrip(itsId:'XZ', capacity:10, breaker:A01b, powerUsed:5, assetType:power).save(failOnError:true)
                    strip7 = new PowerStrip(itsId:'YZ', capacity:10, breaker:A01b, powerUsed:4, assetType:power).save(failOnError:true)
                    strip8 = new PowerStrip(itsId:'XY', capacity:10, breaker:A02a, powerUsed:3, assetType:power).save(failOnError:true)
                    strip9 = new PowerStrip(itsId:'XZ', capacity:10, breaker:A02a, powerUsed:2, assetType:power).save(failOnError:true)
                    strip10 = new PowerStrip(itsId:'YZ', capacity:10, breaker:A02a, powerUsed:1, assetType:power).save(failOnError:true)
                    strip11 = new PowerStrip(itsId:'XY', capacity:10, breaker:A02b, powerUsed:7, assetType:power).save(failOnError:true)
                    strip12 = new PowerStrip(itsId:'XZ', capacity:10, breaker:A02b, powerUsed:5, assetType:power).save(failOnError:true)
                    strip13 = new PowerStrip(itsId:'YZ', capacity:10, breaker:A02b, powerUsed:3, assetType:power).save(failOnError:true)
                }
                def plug1, plug2, plug3, plug4
                if(!DevicePlug.count()) {
                    plug1 = new DevicePlug(strip:strip2, device:aphrodite).save(failOnError:true)
                    plug2 = new DevicePlug(strip:strip2, device:t2k06).save(failOnError:true)
                    plug3 = new DevicePlug(strip:strip3, device:aphrodite).save(failOnError:true)
                    plug4 = new DevicePlug(strip:strip3, device:t2k07).save(failOnError:true)
                }

            }
        }
    }

    def destroy = {
    }
}
