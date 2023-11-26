package io.klebe.ocid

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench
import org.cyclops.integrateddynamics.api.block.IVariableContainer
import org.cyclops.integrateddynamics.api.block.cable.ICable
import org.cyclops.integrateddynamics.api.network.INetworkCarrier
import org.cyclops.integrateddynamics.api.network.INetworkElementProvider
import org.cyclops.integrateddynamics.api.path.IPathElement


@CapabilityInject(IWrench::class)
var WRENCH_CAPABILITY: Capability<IWrench>? = null

@CapabilityInject(ICable::class)
var CABLE_CAPABILITY: Capability<ICable>? = null

@CapabilityInject(INetworkCarrier::class)
var NETWORK_CARRIER_CAPABILITY: Capability<INetworkCarrier>? = null

@CapabilityInject(IPathElement::class)
var PATH_ELEMENT_CAPABILITY: Capability<IPathElement>? = null

@CapabilityInject(IVariableContainer::class)
var VARIABLE_CONTAINER_CAPABILITY: Capability<IVariableContainer>? = null

@CapabilityInject(INetworkElementProvider::class)
var NETWORK_ELEMENT_PROVIDER: Capability<INetworkElementProvider>? = null